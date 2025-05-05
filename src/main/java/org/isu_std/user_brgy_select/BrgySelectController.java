package org.isu_std.user_brgy_select;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.model_builders.BarangayBuilder;

public class BrgySelectController {
    private final BrgySelectService brgySelectService;
    private final BarangayBuilder barangayBuilder;

    protected BrgySelectController(BrgySelectService brgySelectService){
        this.barangayBuilder = brgySelectService.getBarangayBuilder();
        this.brgySelectService = brgySelectService;
    }

    protected void setBrgyInfo(int count, String input){
        switch (count){
            case 0 -> barangayBuilder.barangayName(input);
            case 1 -> barangayBuilder.municipality(input);
            case 2 -> barangayBuilder.province(input);
        }
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
