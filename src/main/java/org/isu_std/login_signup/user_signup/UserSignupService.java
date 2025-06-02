package org.isu_std.login_signup.user_signup;

import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.user_brgy_select.BrgySelectFactory;
import org.isu_std.user_brgy_select.BarangaySelect;
import org.isu_std.models.User;
import org.isu_std.models.model_builders.BuilderFactory;
import org.isu_std.models.model_builders.UserBuilder;
import org.isu_std.user_info_manager.UserInfoManager;

public class UserSignupService{
    private final UserDao userDao;
    private final BarangayDao barangayDao;

    public UserSignupService(UserDao userDao, BarangayDao barangayDao){
        this.userDao = userDao;
        this.barangayDao = barangayDao;
    }

    protected UserBuilder getUserBuilder(){
        return BuilderFactory.createUserBuilder();
    }

    protected int getSelectedBarangayId(){
        BarangaySelect barangaySelect = BrgySelectFactory.createBrgySelect(barangayDao);

        return barangaySelect.getBarangayID();
    }

    protected String[] getUserDetailsWithSpecs(){
        return UserInfoManager.getUserAttrNamesWithSpecs();
    }

    protected void addingUser(User user){
        try {
            if (!userDao.addUser(user)) {
                throw new OperationFailedException(
                        "Failed to create the account! Please try again."
                );
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to insert user : " + user);
        }
    }

    protected void checkInputUsername(String username){
        try {
            UserInfoManager.checkUsername(username);
            UserInfoManager.checkUserIdExistence(userDao, username);
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch userId with user_name : " + username);
        }
    }

    protected void checkInputPassword(String password){
        UserInfoManager.checkPassword(password);
    }
}
