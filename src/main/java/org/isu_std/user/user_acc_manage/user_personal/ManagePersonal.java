package org.isu_std.user.user_acc_manage.user_personal;

import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.user.user_acc_manage.UserManageProcess;

public class ManagePersonal implements UserManageProcess {
    private final ManagePersonalController managePersonalController;

    public ManagePersonal(ManagePersonalController managePersonalController){
        this.managePersonalController = managePersonalController;
    }

    @Override
    public void managePerformed(String manageTitle){
        Util.printSectionTitle(manageTitle);

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
