package org.isu_std.admin.admin_brgy_manage.registeracc;

import org.isu_std.admin.admin_brgy_manage.BarangayConfig;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Barangay;
import org.isu_std.models.model_builders.BarangayBuilder;

public class RegisterBrgyController {
    private static final String[] BRGY_INFORMATIONS_NEEDS = {
            "Barangay Name",
            "Municipality",
            "Province",
            "Pin (Min. %d)".formatted(BarangayConfig.MIN_PIN_LENGTH.getValue())
    };

    private final RegisterBrgyService registerBrgyService;
    private final BarangayBuilder barangayBuilder;

    private Barangay newBarangay;

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
        try{
            Barangay barangay = barangayBuilder.build();
            registerBrgyService.checkBarangayIfUnique(barangay);
            this.newBarangay = barangay;

            return true;
        }catch(IllegalArgumentException | SecurityException e){
            Util.printMessage(e.getMessage());
        }

        return false;
    }

    protected boolean isAddingSuccess(){
        try{
            registerBrgyService.addBarangay(this.newBarangay);
            return true;
        }catch (OperationFailedException | ServiceException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printNewBarangayId(){
        try {
            int barangayId = registerBrgyService.getNewBarangayId(this.newBarangay);

            Util.printInformation("Barangay ID : " + barangayId);
        }catch (ServiceException e){
            Util.printException(e.getMessage());
        }
    }

    protected static String[] getBrgyInfoNeeds(){
        return BRGY_INFORMATIONS_NEEDS;
    }

    protected String getStrBrgyInfo(){
        return "%s, %s, %s".formatted(
                newBarangay.barangayName(),
                newBarangay.municipality(),
                newBarangay.province()
        );
    }
}
