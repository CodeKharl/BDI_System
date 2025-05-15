package org.isu_std;

import org.isu_std.admin.admin_brgy_manage.AdminBrgyAccFactory;
import org.isu_std.client_context.AdminContext;
import org.isu_std.client_context.UserContext;
import org.isu_std.dao.DaoFactory;
import org.isu_std.models.Admin;
import org.isu_std.models.User;
import org.isu_std.user.UserFactory;

public class PostLogNavFactory {
    private final DaoFactory daoFactory;

    public PostLogNavFactory(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    protected PostLoginNavigator getPostLogNav(int type, UserContext userContext, AdminContext adminContext){
        return switch (type){
            case ClientManager.ADMIN_VAL -> {
                var adminBrgyAccFactory = new AdminBrgyAccFactory(daoFactory);
                yield adminBrgyAccFactory.createAdminBrgyAcc(adminContext);
            }

            case ClientManager.USER_VAL -> {
                var userFactory = new UserFactory(daoFactory);
                yield userFactory.createUserUI(userContext);
            }

            default -> throw new IllegalStateException("Unexpected Value " + type);
        };
    }
}
