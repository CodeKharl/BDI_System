package org.isu_std.logsign.usersignup.brgyselection;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class BrgySelection {
    private final String[] BARANGAY_INFO = {"Barangay Name", "Municipality", "Province"};
    private final BrgySelectController brgySelectController;

    public BrgySelection(BrgySelectController brgySelectController){
        this.brgySelectController = brgySelectController;
    }

    public int getBarangayID(){
        Util.printSectionTitle("Barangay Section");

        if(!setBarangayInfo()){
            return -1;
        }

        return brgySelectController.getBarangayID();
    }

    private boolean setBarangayInfo(){
        int count = 0;
        while(count < BARANGAY_INFO.length){
            String input = SystemInput.getStringInput(
                    "Enter your %s (%c == Cancel) : "
                            .formatted(BARANGAY_INFO[count], ChoiceCollection.EXIT_CODE.getValue())
            );

            if(input.charAt(0) == ChoiceCollection.EXIT_CODE.getValue()){
                return false;
            }

            if(brgySelectController.isBrgyInputsAccepted(count, input)){
                count++;
            }
        }

        return true;
    }
}
