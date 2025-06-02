package org.isu_std.admin.admin_brgy_manage;

import org.isu_std.admin.admin_brgy_manage.linkacc.LinkBarangay;
import org.isu_std.admin.admin_brgy_manage.linkacc.LinkBrgyController;
import org.isu_std.admin.admin_brgy_manage.linkacc.LinkBrgyService;
import org.isu_std.admin.admin_brgy_manage.registeracc.RegisterBarangay;
import org.isu_std.admin.admin_brgy_manage.registeracc.RegisterBrgyController;
import org.isu_std.admin.admin_brgy_manage.registeracc.RegisterBrgyService;
import org.isu_std.admin.admin_main.AdminUI;
import org.isu_std.admin.admin_main.AdminUIFactory;
import org.isu_std.client_context.AdminContext;
import org.isu_std.dao.*;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Barangay;

import java.util.Optional;

public class AdminBrgyService {
    private final BarangayDao barangayDao;
    private final AdminDao adminDao;
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;
    private final DocumentRequestDao documentRequestDao;
    private final PaymentDao paymentDao;

    protected AdminBrgyService(
            BarangayDao barangayDao,
            AdminDao adminDao,
            DocManageDao docManageDao,
            DocumentDao documentDao,
            UserPersonalDao userPersonalDao,
            DocumentRequestDao documentRequestDao,
            PaymentDao paymentDao
    ){
        this.barangayDao = barangayDao;
        this.adminDao = adminDao;
        this.docManageDao = docManageDao;
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
        this.documentRequestDao = documentRequestDao;
        this.paymentDao = paymentDao;
    }

    protected RegisterBarangay createRegisterBrgy(){
        var registerBarangayService = new RegisterBrgyService(barangayDao);
        var registerBarangayController = new RegisterBrgyController(registerBarangayService);

        return new RegisterBarangay(registerBarangayController);
    }

    protected LinkBarangay createLinkBrgy(AdminContext adminContext){
        var linkBarangayService = new LinkBrgyService(barangayDao, adminDao);
        var linkBarangayController = new LinkBrgyController(linkBarangayService, adminContext);

        return new LinkBarangay(linkBarangayController);
    }

    protected AdminUI getAdminUI(AdminContext adminContext){
        var adminUIFactory = new AdminUIFactory(
                adminDao,
                docManageDao,
                documentDao,
                documentRequestDao,
                userPersonalDao,
                paymentDao
        );

        return adminUIFactory.createAdmin(
                adminContext,
                getBarangay(adminContext)
        );
    }

    private Barangay getBarangay(AdminContext adminContext){
        int barangayId = adminContext.getAdmin().barangayId();

        try {
            Optional<Barangay> optionalBarangay = this.barangayDao
                    .findOptionalBarangay(barangayId);

            return optionalBarangay.orElseThrow(
                    () -> new NotFoundException("Barangay not found!")
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException(
                    "Failed to fetch barangay with barangay ID : " + barangayId
            );
        }
    }
}
