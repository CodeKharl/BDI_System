package org.isu_std;
import org.isu_std.client_context.AdminContext;
import org.isu_std.client_context.UserContext;
import org.isu_std.dao.DaoFactory;
import org.isu_std.login_signup.LogSignFactory;
import org.isu_std.login_signup.Login;
import org.isu_std.login_signup.Signup;
import org.isu_std.models.Admin;
import org.isu_std.models.User;

public class SystemService {
    private final LogSignFactory logSignFactory;
    private final PostLogNavFactory postLogNavFactory;

    public SystemService(LogSignFactory logSignFactory, PostLogNavFactory postLogNavFactory){
        this.logSignFactory = logSignFactory;
        this.postLogNavFactory = postLogNavFactory;
    }

    protected AdminContext createAdminContext(){
        return new AdminContext();
    }

    protected UserContext createUserContext(){
        return new UserContext();
    }

    protected Login getLogin(int type, AdminContext adminContext, UserContext userContext){
        return logSignFactory.createLoginInsType(type, adminContext, userContext);
    }

    protected Signup getSignup(int type){
        return logSignFactory.createSignupInsType(type);
    }

    protected PostLoginNavigator getPostLoginNav(int type, UserContext userContext, Admin admin){
        return postLogNavFactory.getPostLogNav(type, userContext, admin);
    }
}