package org.isu_std.admin.admin_brgy_manage;

import org.isu_std.admin.admin_brgy_manage.linkacc.LinkBarangay;
import org.isu_std.admin.admin_brgy_manage.linkacc.LinkBrgyController;
import org.isu_std.admin.admin_brgy_manage.linkacc.LinkBrgyService;
import org.isu_std.admin.admin_brgy_manage.registeracc.RegisterBarangay;
import org.isu_std.admin.admin_brgy_manage.registeracc.RegisterBrgyController;
import org.isu_std.admin.admin_brgy_manage.registeracc.RegisterBrgyService;
import org.isu_std.admin.admin_ui.AdminUI;
import org.isu_std.admin.admin_ui.AdminUIFactory;
import org.isu_std.dao.*;
import org.isu_std.io.exception.OperationFailedException;
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

    protected AdminBrgyService(
            BarangayDao barangayDao, AdminDao adminDao, DocManageDao docManageDao,
            DocumentDao documentDao, UserPersonalDao userPersonalDao, DocumentRequestDao documentRequestDao
    ){
        this.barangayDao = barangayDao;
        this.adminDao = adminDao;
        this.docManageDao = docManageDao;
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
        this.documentRequestDao = documentRequestDao;
    }

    protected RegisterBarangay createRegisterBrgy(){
        return new RegisterBarangay(
                new RegisterBrgyController(
                        RegisterBrgyService.getInstance(barangayDao)
                )
        );
    }

    protected LinkBarangay createLinkBrgy(Admin admin){
        return new LinkBarangay(
                new LinkBrgyController(
                        LinkBrgyService.getInstance(barangayDao, adminDao),
                        admin
                )
        );
    }

    protected AdminUI getAdminUi(Admin admin){
        AdminUIFactory adminUIFactory = AdminUIFactory.getInstance(
                docManageDao,
                documentDao,
                documentRequestDao,
                userPersonalDao
        );

        Optional<Barangay> optionalBarangay = barangayDao.getOptionalBarangay(admin.barangayId());
        return adminUIFactory.createAdmin(
                admin,
                optionalBarangay.orElseThrow(
                        () -> new OperationFailedException("Failed to get the barangay informations.")
                )
        );
    }
}
