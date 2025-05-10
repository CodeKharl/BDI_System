package org.isu_std.user_brgy_select;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class BarangaySelect {
    private final String[] BARANGAY_INFO = {"Barangay Name", "Municipality", "Province"};
    private final BrgySelectController brgySelectController;

    protected BarangaySelect(BrgySelectController brgySelectController){
        this.brgySelectController = brgySelectController;
    }

    public int getBarangayID(){
        // 0 = Barangay Not Exist
        int barangayId;
        do {
            Util.printSectionTitle("Barangay Section");

            if (!setBarangayInfo()) return 0;

            barangayId = brgySelectController.getBarangayID();
        }while(barangayId == 0);

        return barangayId;
    }

    private boolean setBarangayInfo(){
        char backValue = ChoiceCollection.EXIT_CODE.getValue();

        for(int i = 0; i < BARANGAY_INFO.length; i++){
            String input = SystemInput.getStringInput(
                    "Enter your %s (%c == Cancel) : ".formatted(BARANGAY_INFO[i], backValue)
            );

            if(input.charAt(0) == backValue){
                return false;
            }

            brgySelectController.setBrgyInfo(i, input);
        }

        return true;
    }
}
