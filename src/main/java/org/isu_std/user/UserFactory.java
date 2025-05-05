package org.isu_std.user;

import org.isu_std.client_context.UserContext;
import org.isu_std.dao.DaoFactory;
import org.isu_std.models.User;

public final class UserFactory {
    private final DaoFactory daoFactory;

    public UserFactory(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    public UserUI createUserUI(UserContext userContext){
        UserService userService = new UserService(
                daoFactory.getDocumentDao(),
                daoFactory.getPersonalDao(),
                daoFactory.getDocumentRequestDao(),
                daoFactory.paymentDao(),
                daoFactory.createBrgyDao(),
                daoFactory.createUserDao()
        );

        UserController userController = new UserController(userService, userContext);
        return new UserUI(userController);
    }
}
