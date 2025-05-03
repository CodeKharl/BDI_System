package org.isu_std.user.user_acc_manage.userbarangay;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.user.user_acc_manage.UserManageProcess;

public class ManageBarangay implements UserManageProcess {
    private final ManageBarangayController manageBarangayController;

    public ManageBarangay(ManageBarangayController manageBarangayController){
        this.manageBarangayController = manageBarangayController;
    }

    @Override
    public void managePerformed(String manageTitle){
        Util.printSectionTitle(manageTitle);

        manageBarangayController.printExistingBrgyInfo();
        if(!modifyConfirmation()){
            return;
        }

        if(!manageBarangayController.setChosenBarangay()){
            return;
        }

        if(!newBrgyConfirmation()){
            return;
        }

        if(manageBarangayController.isChangingBrgySuccess()){
            Util.printInformation(
                    "Your barangay change was successful. " +
                            "All related data will now reflect the updated information."
            );
        }
    }

    private boolean modifyConfirmation(){
        return SystemInput.isPerformConfirmed(
                "Change Barangay",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }

    private boolean newBrgyConfirmation(){
        return SystemInput.isPerformConfirmed(
                "New Barangay Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
