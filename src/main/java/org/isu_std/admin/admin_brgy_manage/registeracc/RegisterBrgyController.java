package org.isu_std.admin.admin_brgy_manage.registeracc;

import org.isu_std.admin.admin_brgy_manage.BarangayConfig;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.modelbuilders.BarangayBuilder;

public class RegisterBrgyController {
    private static final String[] BRGY_INFORMATIONS_NEEDS = {
            "Barangay Name",
            "Municipality",
            "Province",
            "Pin (Min. %d)".formatted(BarangayConfig.MIN_PIN_LENGTH.getValue())
    };

    private final RegisterBrgyService registerBrgyService;
    private final BarangayBuilder barangayBuilder;

    public RegisterBrgyController(RegisterBrgyService registerBrgyService){
        this.registerBrgyService = registerBrgyService;
        this.barangayBuilder = registerBrgyService.getAdminBuilder();

    }

    protected boolean isInputAccepted(int count, String input){
        try {
            switch (count) {
                case 0 -> {
                    registerBrgyService.checkBrgyName(input);
                    barangayBuilder.barangayName(input);
                }

                case 1 -> {
                    registerBrgyService.checkMunicipal_prov(input, BRGY_INFORMATIONS_NEEDS[count]);
                    barangayBuilder.municipality(input);
                }

                case 2 -> {
                    registerBrgyService.checkMunicipal_prov(input, BRGY_INFORMATIONS_NEEDS[count]);
                    barangayBuilder.province(input);
                }

                case 3 -> barangayBuilder.barangayPin(
                        registerBrgyService.getCheckInputBrgyPin(input)
                );

            }

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean isBarangayAccepted(){
        try {
            if(getBarangayId() == 0) return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected int getBarangayId(){
        try{
            return registerBrgyService.getBarangayId(
                    barangayBuilder.build()
            );
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return 0;
    }

    protected boolean isAddingSuccess(){
        try{
            registerBrgyService.addBarangay(barangayBuilder.build());
            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }


    protected static String[] getBrgyInfoNeeds(){
        return BRGY_INFORMATIONS_NEEDS;
    }

    protected static String getStrBrgyInfo(){
        return "%s, %s, %s".formatted(
                BRGY_INFORMATIONS_NEEDS[0],
                BRGY_INFORMATIONS_NEEDS[1],
                BRGY_INFORMATIONS_NEEDS[2]
        );
    }
}
