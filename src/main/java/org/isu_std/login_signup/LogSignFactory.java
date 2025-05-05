package org.isu_std.login_signup;

import org.isu_std.ClientContext;
import org.isu_std.ClientManager;
import org.isu_std.dao.AdminDao;
import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.login_signup.admin_login.AdminLogin;
import org.isu_std.login_signup.admin_login.AdminLoginController;
import org.isu_std.login_signup.admin_login.AdminLoginService;
import org.isu_std.login_signup.admin_signup.AdminSignController;
import org.isu_std.login_signup.admin_signup.AdminSignService;
import org.isu_std.login_signup.admin_signup.AdminSignup;
import org.isu_std.login_signup.user_login.UserLogin;
import org.isu_std.login_signup.user_login.UserLoginController;
import org.isu_std.login_signup.user_login.UserLoginService;
import org.isu_std.login_signup.user_signup.UserSignup;
import org.isu_std.login_signup.user_signup.UserSignupController;
import org.isu_std.login_signup.user_signup.UserSignupService;

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
        return type == ClientManager.ADMIN_VAL ?
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
        return type == ClientManager.ADMIN_VAL ?
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
