package org.isu_std.user;

import org.isu_std.dao.DaoFactory;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.dao.DocumentDao;
import org.isu_std.models.User;

public final class UserFactory {
    private final DaoFactory daoFactory;

    private UserFactory(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    private final static class Holder{
        private static UserFactory instance;
    }

    public static UserFactory getInstance(DaoFactory daoFactory){
        if(Holder.instance == null){
            Holder.instance = new UserFactory(daoFactory);
        }

        return Holder.instance;
    }

    public UserUI createUserUI(User user){
        UserService userService = UserService.getInstance(
                daoFactory.getDocumentDao(),
                daoFactory.getPersonalDao(),
                daoFactory.getDocumentRequestDao()
        );

        UserController userController = new UserController(userService, user);
        return new UserUI(userController);
    }
}
