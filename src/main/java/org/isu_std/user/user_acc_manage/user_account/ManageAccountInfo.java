package org.isu_std.user.user_acc_manage.user_account;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.user.user_acc_manage.UserManageProcess;

public class ManageAccountInfo implements UserManageProcess{
    private final ManageAccInfoController manageAccInfoController;

    public ManageAccountInfo(ManageAccInfoController manageAccInfoController){
        this.manageAccInfoController = manageAccInfoController;
    }

    @Override
    public void managePerformed(String manageTitle){
        Util.printSectionTitle(manageTitle);

        while(true){
            manageAccInfoController.printUserInfo();
            if(!setChosenDetail()){
                return;
            }

            if(!setChosenDetailValue()){
                continue;
            }

            updateUserStart();
        }
    }

    private boolean updateAccountInfoConfirmed(){
        return SystemInput.isPerformConfirmed(
                "Update Information Confirm",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.EXIT_CODE.getValue()
        );
    }

    private boolean setChosenDetail(){
        String[] accInfoDetails = manageAccInfoController.getAccountInfoDetails();
        int backValue = accInfoDetails.length + 1;

        Util.printSectionTitle("Account Information Details");
        Util.printChoices(accInfoDetails);
        Util.printChoice("%d. Back to Account Manage Menu".formatted(backValue));

        int choice = SystemInput.getIntChoice(
                "Enter the detail to update : ",
                backValue
        );

        if(choice == backValue){
            return false;
        }

        manageAccInfoController.setChosenDetail(choice);
        return true;
    }

    private boolean setChosenDetailValue(){
        String chosenDetailWithSpec = manageAccInfoController.getChosenDetailWithSpec();
        char cancelValue = ChoiceCollection.EXIT_CODE.getValue();

        while(true){
            String value = SystemInput.getStringInput(
                    "Enter your new %s (%c == cancel) : "
                            .formatted(chosenDetailWithSpec, cancelValue)
            );

            if(value.charAt(0) == cancelValue){
                return false;
            }

            if(manageAccInfoController.isValueAccepted(value)){
                return true;
            }
        }
    }

    private void updateUserStart(){
        if(!updateAccountInfoConfirmed()){
            return;
        }

        if(manageAccInfoController.isUpdateSuccess()){
            Util.printMessage("Success! Your account information has been updated.");
        }
    }
}
