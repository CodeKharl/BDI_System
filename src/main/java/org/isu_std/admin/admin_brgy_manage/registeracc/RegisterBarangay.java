package org.isu_std.admin.admin_brgy_manage.registeracc;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Symbols;
import org.isu_std.io.Util;

public class RegisterBarangay{
    private final RegisterBrgyController registerBrgyController;

    public RegisterBarangay(RegisterBrgyController registerBrgyController){
        this.registerBrgyController = registerBrgyController;
    }

    public void registerPerformed() {
        Util.printSectionTitle("Barangay Register Account");

        while(true){
            if(!setInformations(RegisterBrgyController.getBrgyInfoNeeds())){
                return;
            }

            if(!registerBrgyController.isBarangayAccepted()){
                continue;
            }

            if(isRegisteringConfirmed()){
                printSuccessMessage(registerBrgyController.isAddingSuccess());
            }
        }
    }

    private boolean setInformations(String[] brgyInfoNeeds){
        int count = 0;
        while (count < brgyInfoNeeds.length) {
            String input = SystemInput.getStringInput("Enter a %s (Cancel == %s) : "
                    .formatted(brgyInfoNeeds[count], ChoiceCollection.EXIT_CODE.getValue())
            ).toUpperCase();

            if (input.charAt(0) == ChoiceCollection.EXIT_CODE.getValue()) {
                return false;
            }

            if (registerBrgyController.isInputAccepted(count, input)) {
                count++;
            }
        }

        return true;
    }

    protected void printSuccessMessage(boolean isRegisteringSuccess){
        if(!isRegisteringSuccess){
            return;
        }

        Util.printMessage(
                (
                    """
                        You successfully register the barangay %s
                        %sBarangay ID : %d"""
                ).formatted(
                        RegisterBrgyController.getStrBrgyInfo(),
                        Symbols.MESSAGE.getType(),
                        registerBrgyController.getBarangayId()
                )
        );
    }

    private boolean isRegisteringConfirmed(){
        return SystemInput.isPerformConfirmed(
                "Register Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
