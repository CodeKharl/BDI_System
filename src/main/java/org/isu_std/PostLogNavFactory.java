package org.isu_std;

import org.isu_std.admin.admin_brgy_manage.AdminBrgyAccFactory;
import org.isu_std.dao.DaoFactory;
import org.isu_std.models.Admin;
import org.isu_std.models.User;
import org.isu_std.user.UserFactory;

public class PostLogNavFactory {
    private final DaoFactory daoFactory;

    private PostLogNavFactory(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    private final static class Holder{
        private static PostLogNavFactory postLogNavFactory;
    }

    public static PostLogNavFactory getInstance(DaoFactory daoFactory){
        if(Holder.postLogNavFactory == null){
            Holder.postLogNavFactory = new PostLogNavFactory(daoFactory);
        }

        return Holder.postLogNavFactory;
    }

    protected PostLoginNavigator getPostLogNav(int type, User user, Admin admin){
        return type == ClientManager.ADMIN_VAL ?
                AdminBrgyAccFactory
                        .getInstance(daoFactory)
                        .createAdminBrgyAcc(admin) :

                UserFactory
                        .getInstance(daoFactory)
                        .createUserUI(user);
    }
}
