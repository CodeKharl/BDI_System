package org.isu_std.login_signup.user_login;

import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.User;
import org.isu_std.dao.UserDao;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.user_info_manager.UserInfoManager;

import java.util.Optional;

public class UserLoginService{
    private final UserDao userDao;

    public UserLoginService(UserDao userDao){
        this.userDao = userDao;
    }

    protected String[] getUserDetails(){
        return UserInfoManager.getUserAttributeNames();
    }

    protected User getUser(String userName){
        try {
            Optional<User> user = userDao.getOptionalUser(userName);

            return user.orElseThrow(
                    () -> new NotFoundException(
                            InputMessageCollection.INPUT_OBJECT_NOT_EXIST.getFormattedMessage("User")
                    )
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch user with user_name : " + userName);
        }
    }

    public void checkUserPassword(String actualPass, String inputPass){
        if(!actualPass.equals(inputPass)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NOT_EQUAL.getFormattedMessage("password")
            );
        }
    }
}
