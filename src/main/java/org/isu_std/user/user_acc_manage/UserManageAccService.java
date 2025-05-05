package org.isu_std.user.user_acc_manage;

import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.models.User;
import org.isu_std.user.user_acc_manage.user_account.ManageAccInfoController;
import org.isu_std.user.user_acc_manage.user_account.ManageAccInfoService;
import org.isu_std.user.user_acc_manage.user_account.ManageAccountInfo;
import org.isu_std.user.user_acc_manage.user_barangay.ManageBarangay;
import org.isu_std.user.user_acc_manage.user_barangay.ManageBarangayController;
import org.isu_std.user.user_acc_manage.user_barangay.ManageBarangayService;
import org.isu_std.user.user_acc_manage.user_personal.ManagePersonal;
import org.isu_std.user.user_acc_manage.user_personal.ManagePersonalController;
import org.isu_std.user.user_acc_manage.user_personal.ManagePersonalService;

public class UserManageAccService {
    private final UserPersonalDao userPersonalDao;
    private final BarangayDao barangayDao;
    private final UserDao userDao;

    protected UserManageAccService(UserPersonalDao userPersonalDao, BarangayDao barangayDao, UserDao userDao){
        this.userPersonalDao = userPersonalDao;
        this.barangayDao = barangayDao;
        this.userDao = userDao;
    }

    protected UserManageProcess[] createUserManageProcesses(User user){
        ManagePersonal managePersonal = createManagePersonal(user);
        ManageAccountInfo manageAccountInfo = createManageAccountInfo(user);
        ManageBarangay manageBarangay = createManageBarangay(user);

        return new UserManageProcess[]{managePersonal, manageAccountInfo, manageBarangay};
    }

    private ManagePersonal createManagePersonal(User user){
        var managePersonalService = new ManagePersonalService(userPersonalDao);
        var managePersonalController = new ManagePersonalController(managePersonalService, user);
        return new ManagePersonal(managePersonalController);
    }

    private ManageAccountInfo createManageAccountInfo(User user){
        var manageAccountInfoService = new ManageAccInfoService(userDao);
        var manageAccountInfoController = new ManageAccInfoController(manageAccountInfoService, user);
        return new ManageAccountInfo(manageAccountInfoController);
    }

    private ManageBarangay createManageBarangay(User user){
        var manageBarangayService = new ManageBarangayService(barangayDao, userDao);
        var manageBarangayController = new ManageBarangayController(manageBarangayService, user);

        return new ManageBarangay(manageBarangayController);
    }
}
