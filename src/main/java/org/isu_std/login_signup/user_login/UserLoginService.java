package org.isu_std.login_signup.user_login;

import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.dynamic_enum_handler.EnumValueProvider;
import org.isu_std.models.User;
import org.isu_std.dao.UserDao;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.user_info_manager.UserInfoConfig;

import java.util.Optional;

public class UserLoginService{
    private final UserDao userDao;

    private UserLoginService(UserDao userDao){
        this.userDao = userDao;
    }

    private static final class Holder{
        private static UserLoginService instance;
    }

    public static UserLoginService getInstance(UserDao userDao){
        if(Holder.instance == null){
            Holder.instance = new UserLoginService(userDao);
        }

        return Holder.instance;
    }

    protected String[] getUserDetails(){
        return EnumValueProvider.getStringArrValue(
                UserInfoConfig.USER_DETAILS.getValue()
        );
    }

    protected User getUser(String userName){
        Optional<User> user = userDao.getOptionalUser(userName);
        return user.orElseThrow(
                () -> new NotFoundException(
                        InputMessageCollection.INPUT_OBJECT_NOT_EXIST.getFormattedMessage("User")
                )
        );
    }

    public void checkUserPassword(String actualPass, String inputPass){
        if(!actualPass.equals(inputPass)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NOT_EQUAL.getFormattedMessage("password")
            );
        }
    }
}
