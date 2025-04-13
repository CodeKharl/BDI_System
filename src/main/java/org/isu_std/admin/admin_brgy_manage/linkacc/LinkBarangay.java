package org.isu_std.admin.admin_brgy_manage.linkacc;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class LinkBarangay{
    private final String[] NEED_INFORMATIONS = {"Barangay ID", "Pin"};

    private final LinkBrgyController linkBrgyController;

    public LinkBarangay(LinkBrgyController linkBrgyController){
        this.linkBrgyController = linkBrgyController;
    }

    public boolean linkPerformed() {
        Util.printSectionTitle("Barangay Link Account");

        int count = 0;
        while(count < NEED_INFORMATIONS.length){
            int input = SystemInput.getIntInput(
                    "%s (Cancel == %d) : ".formatted(NEED_INFORMATIONS[count], ChoiceCollection.EXIT_INT_CODE.getIntValue())
            );

            if(input == ChoiceCollection.EXIT_INT_CODE.getIntValue()){
                return false;
            }

            if(linkBrgyController.isInputAccepted(count, input)){
                count++;
            }
        }

        return isLinkConfirmed();
    }

    private boolean isLinkConfirmed(){
        if(!SystemInput.isPerformConfirmed(
                "Link Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue())
        ) {
            return false;
        }

        return linkBrgyController.setBarangayConnection();
    }
}
