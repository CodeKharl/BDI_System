package org.isu_std.user.user_acc_manage.user_account;

import org.isu_std.client_context.UserContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.User;
import org.isu_std.models.model_builders.UserBuilder;

public class ManageAccInfoController {
    private final ManageAccInfoService manageAccInfoService;
    private final AccountInfoContext accountInfoContext;
    private final String[] userDetails;

    public ManageAccInfoController(ManageAccInfoService manageAccInfoService, UserContext userContext){
        this.manageAccInfoService = manageAccInfoService;
        this.accountInfoContext = manageAccInfoService.createAccountInfoContext(userContext);
        this.userDetails = manageAccInfoService.getUserDetails();
    }

    protected void printUserInfo(){
        accountInfoContext.
                getUserContext()
                .getUser()
                .printUserDetails();
    }

    protected String[] getAccountInfoDetails(){
        return this.userDetails;
    }

    protected void setChosenDetail(int choice){
        int index = choice - 1;
        String spec = manageAccInfoService.getChosenDetailSpecs(index);
        accountInfoContext.setChosenDetail(userDetails[index]);
        accountInfoContext.setChosenDetailSpec(spec);
    }

    protected String getChosenDetailWithSpec(){
        return accountInfoContext.getUserDetailWithSpec();
    }

    protected boolean isValueAccepted(String input){
        try{
            String chosenDetail = accountInfoContext.getChosenDetail();
            UserBuilder userBuilder = accountInfoContext.getUserBuilder();
            setUserBuilderValues(chosenDetail, userBuilder, input);
            return true;
        }catch(IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void setUserBuilderValues(String chosenDetail, UserBuilder userBuilder, String input)
            throws IllegalArgumentException{
        // 0 == username, 1 == password
        if(chosenDetail.equals(userDetails[0])) {
            manageAccInfoService.checkInputUserName(input);
            userBuilder.username(input);
        } else if(chosenDetail.equals(userDetails[1])){
            manageAccInfoService.checkInputPassword(input);
            userBuilder.password(input);
        }else {
            throw new IllegalStateException(
                    "The string arr user details doesn't contain the chosen detail : " + chosenDetail
            );
        }
    }

    protected boolean isUpdateSuccess(){
        String chosenDetail = accountInfoContext.getChosenDetail();
        UserBuilder userBuilder = accountInfoContext.getUserBuilder();
        User actualUser = accountInfoContext.getUserContext().getUser();

        if(launchUserUpdate(chosenDetail, actualUser, userBuilder)){
            // Update on active user object
            actualUser = manageAccInfoService.createNewUser(actualUser, userBuilder);

            accountInfoContext
                    .getUserContext()
                    .setUser(actualUser);

            return true;
        }

        return false;
    }

    protected boolean launchUserUpdate(String chosenDetail, User actualUser, UserBuilder userBuilder){
        try{
            // Update on the database
            User incompletedUser = userBuilder
                    .userId(actualUser.userId())
                    .build();
            
            this.manageAccInfoService.updateUserPerform(chosenDetail, incompletedUser);
            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
