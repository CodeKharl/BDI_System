package org.isu_std.user.user_acc_manage.user_account;

import org.isu_std.client_context.UserContext;
import org.isu_std.dao.UserDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.User;
import org.isu_std.models.model_builders.BuilderFactory;
import org.isu_std.models.model_builders.UserBuilder;
import org.isu_std.user_info_manager.UserInfoManager;

public class ManageAccInfoService {
    private final UserDao userDao;

    public ManageAccInfoService(UserDao userDao){
        this.userDao = userDao;
    }

    protected AccountInfoContext createAccountInfoContext(UserContext userContext){
        return new AccountInfoContext(userContext, BuilderFactory.createUserBuilder());
    }

    protected String[] getUserAttributeNames(){
        return UserInfoManager.getUserAttributeNames();
    }

    protected String getChosenAttributeSpec(int index){
        String[] specs = UserInfoManager.getUserAttributeSpecs();

        return specs[index];
    }

    protected void checkInputUserName(String username) throws IllegalArgumentException{
        UserInfoManager.checkUsername(username);
        UserInfoManager.checkUserIdExistence(userDao, username);
    }

    protected void checkInputPassword(String password) throws IllegalArgumentException{
        UserInfoManager.checkPassword(password);
    }

    protected User createNewUser(User prevUser, UserBuilder userBuilder){
        userBuilder
                .userId(prevUser.userId())
                .barangayId(prevUser.barangayId());

        // Name and password are the only updating.
        if(userBuilder.getUsername() == null){
            userBuilder.username(prevUser.username());
        } else if(userBuilder.getPassword() == null) {
            userBuilder.password(prevUser.password());
        } else {
            throw new IllegalStateException("Both username and password contains value");
        }

        return userBuilder.build();
    }

    protected void updateUserPerform(String chosenAttributeName, User user){
        try {
            if (!userDao.updateUserInfo(chosenAttributeName, user)) {
                throw new OperationFailedException(
                        "Failed to update account info! Please try again."
                );
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to update user by %s".formatted(chosenAttributeName));
        }
    }
}
