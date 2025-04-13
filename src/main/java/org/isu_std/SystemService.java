package org.isu_std;
import org.isu_std.dao.DaoFactory;
import org.isu_std.logsign.LogSignFactory;
import org.isu_std.logsign.Login;
import org.isu_std.logsign.Signup;
import org.isu_std.models.Admin;
import org.isu_std.models.User;

public class SystemService {
    private final DaoFactory daoFactory;

    public SystemService(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    protected ClientManager createClientManager(){
        return new ClientManager();
    }

    protected Login getLogin(int type, ClientManager clientManager){
        return LogSignFactory.getInstance(
                daoFactory.getUserDao(),
                daoFactory.getAdminDao(),
                daoFactory.getBrgyDao()
        ).createLoginInsType(type, clientManager);
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