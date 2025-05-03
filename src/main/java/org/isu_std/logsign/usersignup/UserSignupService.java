package org.isu_std.logsign.usersignup;

import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.user_brgy_select.BrgySelectController;
import org.isu_std.user_brgy_select.BrgySelectFactory;
import org.isu_std.user_brgy_select.BrgySelectService;
import org.isu_std.user_brgy_select.BrgySelect;
import org.isu_std.models.User;
import org.isu_std.models.modelbuilders.BuilderFactory;
import org.isu_std.models.modelbuilders.UserBuilder;

public class UserSignupService{
    private final UserDao userDao;
    private final BarangayDao barangayDao;

    private final static int MIN_USERNAME_LENGTH = 8;
    private final static int MIN_PASSWORD_LENGTH = 6;

    public UserSignupService(UserDao userDao, BarangayDao barangayDao){
        this.userDao = userDao;
        this.barangayDao = barangayDao;
    }

    private static final class Holder{
        private static UserSignupService instance;
    }

    public static UserSignupService getInstance(UserDao userDao, BarangayDao barangayDao){
        if(Holder.instance == null){
            Holder.instance = new UserSignupService(userDao, barangayDao);
        }

        return Holder.instance;
    }

    protected UserBuilder getUserBuilder(){
        return BuilderFactory.createUserBuilder();
    }

    public void checkInputUsername(String username){
        if(!Validation.isInputLengthAccepted(MIN_USERNAME_LENGTH, username)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("username")
            );
        }
    }

    public void checkInputPassword(String password){
        if (!Validation.isInputLengthAccepted(MIN_PASSWORD_LENGTH, password)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("password")
            );
        }
    }

    public void checkUserIdExistence(String input){
        // !0 means the id is not exist.
        if(userDao.getUserId(input) != 0){
            throw new IllegalArgumentException(
                    "The username (%s) is already exists! Please enter different one."
                            .formatted(input)
            );
        }
    }

    public BrgySelect createBrgySelection(){
        return BrgySelectFactory.createBrgySelect(barangayDao);
    }

    public void addingUser(User user){
        if(!userDao.addUser(user)){
            throw new OperationFailedException("Failed to create the account! Please try again.");
        }
    }

    public static int getMinUsernameLength(){
        return MIN_USERNAME_LENGTH;
    }

    public static int getMinPasswordLength(){
        return MIN_PASSWORD_LENGTH;
    }
}
