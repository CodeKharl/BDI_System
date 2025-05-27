package org.isu_std.login_signup;

import org.isu_std.client_context.AdminContext;
import org.isu_std.ClientManager;
import org.isu_std.client_context.UserContext;
import org.isu_std.dao.AdminDao;
import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.login_signup.admin_login.AdminLogin;
import org.isu_std.login_signup.admin_login.AdminLoginController;
import org.isu_std.login_signup.admin_login.AdminLoginService;
import org.isu_std.login_signup.admin_signup.AdminSignupController;
import org.isu_std.login_signup.admin_signup.AdminSignupService;
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

    public LogSignFactory(UserDao userDao, AdminDao adminDao, BarangayDao barangayDao){
        this.userDao = userDao;
        this.adminDao = adminDao;
        this.barangayDao = barangayDao;
    }

    public Login createLoginInsType(int type, AdminContext adminContext, UserContext userContext){
        return switch (type){
            case ClientManager.ADMIN_VAL -> {
                var adminLoginService = new AdminLoginService(adminDao);
                var adminLoginController = new AdminLoginController(adminLoginService, adminContext);

                yield  new AdminLogin(adminLoginController);
            }

            case ClientManager.USER_VAL -> {
                var userLoginService = new UserLoginService(userDao);
                var userLoginController = new UserLoginController(userLoginService, userContext);

                yield new UserLogin(userLoginController);
            }

            default -> throw new IllegalStateException("Unexpected Value : " + type);
        };
    }

    public Signup createSignupInsType(int type){
        return switch (type){
            case ClientManager.ADMIN_VAL -> {
                var adminSignupService = new AdminSignupService(adminDao);
                var adminLoginController = new AdminSignupController(adminSignupService);

                yield  new AdminSignup(adminLoginController);
            }

            case ClientManager.USER_VAL -> {
                var userSignupService = new UserSignupService(userDao, barangayDao);
                var userSignupController = new UserSignupController(userSignupService);

                yield new UserSignup(userSignupController);
            }

            default -> throw new IllegalStateException("Unexpected Value : " + type);
        };
    }
}
