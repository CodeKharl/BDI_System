package org.isu_std.logsign.usersignup.brgyselection;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.modelbuilders.BarangayBuilder;

public class BrgySelectController {
    private final BrgySelectService brgySelectService;
    private final BarangayBuilder barangayBuilder;

    public BrgySelectController(BrgySelectService brgySelectService){
        this.barangayBuilder = brgySelectService.getBarangayBuilder();
        this.brgySelectService = brgySelectService;
    }

    protected boolean isBrgyInputsAccepted(int count, String input){
        switch (count){
            case 1 -> barangayBuilder.barangayName(input);
            case 2 -> barangayBuilder.municipality(input);
            case 3 -> barangayBuilder.province(input);
        }

        return true;
    }

    protected int getBarangayID(){
        try {
            return brgySelectService.getUserBarangayId(barangayBuilder.build());
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return 0;
    }
}
