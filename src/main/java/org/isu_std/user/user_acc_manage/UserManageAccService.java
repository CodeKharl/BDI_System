package org.isu_std.user.user_acc_manage;

import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.user.user_acc_manage.useraccount.ManageAccountInfo;
import org.isu_std.user.user_acc_manage.userbarangay.ManageBarangay;
import org.isu_std.user.user_acc_manage.userbarangay.ManageBarangayController;
import org.isu_std.user.user_acc_manage.userbarangay.ManageBarangayService;
import org.isu_std.user.user_acc_manage.userpersonal.ManagePersonal;
import org.isu_std.user.user_acc_manage.userpersonal.ManagePersonalController;
import org.isu_std.user.user_acc_manage.userpersonal.ManagePersonalService;

public class UserManageAccService {
    private final UserPersonalDao userPersonalDao;
    private final BarangayDao barangayDao;

    protected UserManageAccService(UserPersonalDao userPersonalDao, BarangayDao barangayDao){
        this.userPersonalDao = userPersonalDao;
        this.barangayDao = barangayDao;
    }

    protected UserManageProcess[] createUserManageProcesses(int userId){
        ManagePersonal managePersonal = createManagePersonal(userId);
        ManageAccountInfo manageAccountInfo = createManageAccountInfo();
        ManageBarangay manageBarangay = createManageBarangay();

        return new UserManageProcess[]{managePersonal, manageAccountInfo, manageBarangay};
    }

    private ManagePersonal createManagePersonal(int userId){
        var managePersonalService = new ManagePersonalService(userPersonalDao);
        var managePersonalController = new ManagePersonalController(managePersonalService, userId);
        return new ManagePersonal(managePersonalController);
    }

    private ManageAccountInfo createManageAccountInfo(){

        return new ManageAccountInfo();
    }

    private ManageBarangay createManageBarangay(){
        var manageBarangayService = new ManageBarangayService(barangayDao);
        var manageBarangayController = new ManageBarangayController(manageBarangayService);

        return new ManageBarangay(manageBarangayController);
    }
}
