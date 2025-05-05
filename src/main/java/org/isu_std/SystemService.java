package org.isu_std;
import org.isu_std.dao.DaoFactory;
import org.isu_std.login_signup.LogSignFactory;
import org.isu_std.login_signup.Login;
import org.isu_std.login_signup.Signup;
import org.isu_std.models.Admin;
import org.isu_std.models.User;

public class SystemService {
    private final DaoFactory daoFactory;

    public SystemService(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    protected ClientContext createClientManager(){
        return new ClientContext();
    }

    protected Login getLogin(int type, ClientContext clientContext){
        return LogSignFactory.getInstance(
                daoFactory.getUserDao(),
                daoFactory.getAdminDao(),
                daoFactory.getBrgyDao()
        ).createLoginInsType(type, clientContext);
    }

    protected Signup getSignup(int type){
        return LogSignFactory.getInstance(
                daoFactory.getUserDao(),
                daoFactory.getAdminDao(),
                daoFactory.getBrgyDao()
        ).createSignupInsType(type);
    }

    protected PostLoginNavigator getPostLoginNav(int type, User user, Admin admin){
        return PostLogNavFactory
                .getInstance(daoFactory)
                .getPostLogNav(type, user, admin);
    }
}