package org.isu_std.login_signup.user_signup;

import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.user_brgy_select.BrgySelectFactory;
import org.isu_std.user_brgy_select.BrgySelect;
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

    protected BrgySelect createBrgySelection(){
        return BrgySelectFactory.createBrgySelect(barangayDao);
    }

    protected String[] getUserDetailsWithSpecs(){
        return UserInfoManager.getUserDetailWithSpecs();
    }

    protected void addingUser(User user){
        if(!userDao.addUser(user)){
            throw new OperationFailedException("Failed to create the account! Please try again.");
        }
    }

    protected void checkInputUsername(String username) throws IllegalArgumentException{
        UserInfoManager.checkUsername(username);
        UserInfoManager.checkUserIdExistence(userDao, username);
    }

    protected void checkInputPassword(String password) throws IllegalArgumentException {
        UserInfoManager.checkPassword(password);
    }
}
