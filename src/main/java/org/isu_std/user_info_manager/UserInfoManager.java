package org.isu_std.user_info_manager;

import org.isu_std.dao.UserDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.Validation;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.io.dynamic_enum_handler.EnumValueProvider;

import java.util.Optional;

public class UserInfoManager {
    public static String[] getUserDetailWithSpecs(){
        String[] userDetails = EnumValueProvider.getStringArrValue(
                UserInfoConfig.USER_DETAILS.getValue()
        );

        String[] specs = EnumValueProvider.getStringArrValue(
                UserInfoConfig.USER_DETAIL_SPECS.getValue()
        );

        var newArr = new String[userDetails.length];

        for(int i = 0; i < userDetails.length; i++){
            newArr[i] = "%s (%s)".formatted(userDetails[i], specs[i]);
        }

        return newArr;
    }

    public static String[] getUserDetails(){
        return EnumValueProvider.getStringArrValue(
                UserInfoConfig.USER_DETAILS.getValue()
        );
    }

    public static void checkUsername(String username){
        int minUsernameLength = EnumValueProvider.getIntValue(
                UserInfoConfig.MIN_USERNAME_LENGTH.getValue()
        );

        if(!Validation.isInputLengthAccepted(minUsernameLength, username)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("username")
            );
        }
    }

    public static void checkPassword(String password){
        int minPasswordLength = EnumValueProvider.getIntValue(
                UserInfoConfig.MIN_PASSWORD_LENGTH.getValue()
        );

        if (!Validation.isInputLengthAccepted(minPasswordLength, password)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("password")
            );
        }
    }

    public static void checkUserIdExistence(UserDao userDao, String username){
        // !0 means the id is not exist.
        try {
            Optional<Integer> getOptionalUserId = userDao.findUserId(username);

            if (getOptionalUserId.isPresent()) {
                throw new IllegalArgumentException(
                        "The username (%s) is already exists! Please enter different one.".formatted(username)
                );
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch userId with username : " + username);
        }
    }
}
