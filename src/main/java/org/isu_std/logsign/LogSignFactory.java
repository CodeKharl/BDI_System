package org.isu_std.logsign;

import org.isu_std.ClientContext;
import org.isu_std.ClientManage;
import org.isu_std.dao.AdminDao;
import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.logsign.adminlogin.AdminLogin;
import org.isu_std.logsign.adminlogin.AdminLoginController;
import org.isu_std.logsign.adminlogin.AdminLoginService;
import org.isu_std.logsign.adminsignup.AdminSignController;
import org.isu_std.logsign.adminsignup.AdminSignService;
import org.isu_std.logsign.adminsignup.AdminSignup;
import org.isu_std.logsign.userlogin.UserLogin;
import org.isu_std.logsign.userlogin.UserLoginController;
import org.isu_std.logsign.userlogin.UserLoginService;
import org.isu_std.logsign.usersignup.UserSignup;
import org.isu_std.logsign.usersignup.UserSignupController;
import org.isu_std.logsign.usersignup.UserSignupService;

public final class LogSignFactory {
    private final UserDao userDao;
    private final AdminDao adminDao;
    private final BarangayDao barangayDao;

    private LogSignFactory(UserDao userDao, AdminDao adminDao, BarangayDao barangayDao){
        this.userDao = userDao;
        this.adminDao = adminDao;
        this.barangayDao = barangayDao;
    }

    private static final class Holder{
        private static LogSignFactory instance;
    }

    public static LogSignFactory getInstance(UserDao userDao, AdminDao adminDao, BarangayDao barangayDao){
        if(Holder.instance == null){
            Holder.instance = new LogSignFactory(userDao, adminDao, barangayDao);
        }

        return Holder.instance;
    }

    public Login createLoginInsType(int type, ClientContext clientContext){
        return type == ClientManage.ADMIN_VAL ?
                new AdminLogin(
                        new AdminLoginController(
                                AdminLoginService.getInstance(adminDao),
                                clientContext
                        )
                ) :
                new UserLogin(
                        new UserLoginController(
                                UserLoginService.getInstance(userDao),
                                clientContext
                        )
                );
    }

    public Signup createSignupInsType(int type){
        return type == ClientManage.ADMIN_VAL ?
                new AdminSignup(
                        new AdminSignController(
                                AdminSignService.getInstance(adminDao)
                        )
                ) :
                new UserSignup(
                        new UserSignupController(
                                UserSignupService.getInstance(userDao, barangayDao)
                        )
                );
    }
}
