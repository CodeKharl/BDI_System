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
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Admin;
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
            BarangayDao barangayDao, AdminDao adminDao, DocManageDao docManageDao,
            DocumentDao documentDao, UserPersonalDao userPersonalDao, DocumentRequestDao documentRequestDao,
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
        return new RegisterBarangay(
                new RegisterBrgyController(
                        RegisterBrgyService.getInstance(barangayDao)
                )
        );
    }

    protected LinkBarangay createLinkBrgy(AdminContext adminContext){
        return new LinkBarangay(
                new LinkBrgyController(
                        LinkBrgyService.getInstance(barangayDao, adminDao),
                        adminContext
                )
        );
    }

    protected AdminUI getAdminUi(AdminContext adminContext) throws OperationFailedException{
        AdminUIFactory adminUIFactory = AdminUIFactory.getInstance(
                docManageDao,
                documentDao,
                documentRequestDao,
                userPersonalDao,
                paymentDao
        );

        try {
            return adminUIFactory.createAdmin(
                    adminContext, getBarangay(adminContext)
            );
        }catch (NotFoundException e){
            throw new OperationFailedException("Failed to create and get admin contents!", e);
        }
    }

    private Barangay getBarangay(AdminContext adminContext){
        Optional<Barangay> optionalBarangay = barangayDao.getOptionalBarangay(
                adminContext.getAdmin().barangayId()
        );

        return optionalBarangay.orElseThrow(
                () -> new NotFoundException("Barangay not found!")
        );
    }
}
