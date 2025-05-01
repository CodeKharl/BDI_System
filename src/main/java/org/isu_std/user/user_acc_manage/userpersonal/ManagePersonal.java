package org.isu_std.user.user_acc_manage.userpersonal;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class ManagePersonal {
    private final ManagePersonalController managePersonalController;

    public ManagePersonal(ManagePersonalController managePersonalController){
        this.managePersonalController = managePersonalController;
    }

    public void manageUserPersonal(){
        Util.printSectionTitle("Manage Personal Information");

        // Info Exist -> Modify
        if(managePersonalController.setUserPersonal()){
            modifyInfoPerformed();
            return;
        }

        // Info not Exits -> fill up information needs.
        if(isCreatingInfoConfirm()){
            managePersonalController.userPersonalCreation();
        }
    }

    protected void modifyInfoPerformed(){
        managePersonalController.printExistingInfos();
        if(isModifyConfirm()){
            managePersonalController.userPersonalModification();
        }
    }

    protected boolean isModifyConfirm(){
        return SystemInput.isPerformConfirmed(
                "Modify Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }

    protected boolean isCreatingInfoConfirm(){
        return SystemInput.isPerformConfirmed(
                "Info Fill-Up Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }

}
