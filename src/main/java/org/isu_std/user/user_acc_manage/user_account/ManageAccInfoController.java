package org.isu_std.user.user_acc_manage.user_account;

import org.isu_std.client_context.UserContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.User;
import org.isu_std.models.model_builders.UserBuilder;

public class ManageAccInfoController {
    private final ManageAccInfoService manageAccInfoService;
    private final AccountInfoContext accountInfoContext;
    private final String[] userAttributeNames;

    public ManageAccInfoController(ManageAccInfoService manageAccInfoService, UserContext userContext){
        this.manageAccInfoService = manageAccInfoService;
        this.accountInfoContext = manageAccInfoService.createAccountInfoContext(userContext);
        this.userAttributeNames = manageAccInfoService.getUserAttributeNames();
    }

    protected void printUserInfo(){
        accountInfoContext.
                getUserContext()
                .getUser()
                .printUserDetails();
    }

    protected String[] getAccInfoAttributeNames(){
        return this.userAttributeNames;
    }

    protected void setChosenAttribute(int choice){
        int index = choice - 1;
        String spec = manageAccInfoService.getChosenAttributeSpec(index);

        accountInfoContext.setChosenAttributeName(userAttributeNames[index]);
        accountInfoContext.setChosenAttributeSpec(spec);
    }

    protected String getChosenAttrNameWithSpec(){
        return accountInfoContext.getUserAttrNameWithSpec();
    }

    protected boolean isValueAccepted(String input){
        try{
            String chosenAttributeName = accountInfoContext.getChosenAttributeName();
            UserBuilder userBuilder = accountInfoContext.getUserBuilder();

            setUserBuilderValues(chosenAttributeName, userBuilder, input);

            return true;
        }catch(IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void setUserBuilderValues(String chosenAttributeName, UserBuilder userBuilder, String input)
            throws IllegalArgumentException{
        // 0 == username, 1 == password
        if(chosenAttributeName.equals(userAttributeNames[0])) {
            manageAccInfoService.checkInputUserName(input);
            userBuilder.username(input);
        } else if(chosenAttributeName.equals(userAttributeNames[1])){
            manageAccInfoService.checkInputPassword(input);
            userBuilder.password(input);
        } else {
            throw new IllegalStateException(
                    "The string arr user details doesn't contain the chosen detail : " + chosenAttributeName
            );
        }
    }

    protected boolean isUpdateSuccess(){
        String chosenAttributeName = accountInfoContext.getChosenAttributeName();
        UserBuilder userBuilder = accountInfoContext.getUserBuilder();
        User actualUser = accountInfoContext.getUserContext().getUser();

        if(updateUser(chosenAttributeName, actualUser, userBuilder)){
            // Update on active user object
            actualUser = manageAccInfoService.createNewUser(actualUser, userBuilder);

            accountInfoContext
                    .getUserContext()
                    .setUser(actualUser);

            accountInfoContext.getUserBuilder().resetAllValues();

            return true;
        }

        return false;
    }

    protected boolean updateUser(String chosenAttributeName, User actualUser, UserBuilder userBuilder){
        try{
            // Update on the database
            User incompleteUser = userBuilder
                    .userId(actualUser.userId())
                    .build();
            
            this.manageAccInfoService.updateUserPerform(chosenAttributeName, incompleteUser);
            return true;
        }catch (ServiceException | OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
