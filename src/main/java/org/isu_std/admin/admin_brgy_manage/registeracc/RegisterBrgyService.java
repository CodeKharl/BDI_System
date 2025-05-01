package org.isu_std.admin.admin_brgy_manage.registeracc;

import org.isu_std.admin.admin_brgy_manage.BarangayConfig;
import org.isu_std.dao.BarangayDao;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Barangay;
import org.isu_std.models.modelbuilders.BarangayBuilder;
import org.isu_std.models.modelbuilders.BuilderFactory;

public class RegisterBrgyService {
    private final BarangayDao barangayDao;

    private RegisterBrgyService(BarangayDao barangayDao){
        this.barangayDao = barangayDao;
    }

    protected final static class Holder{
        private static RegisterBrgyService instance;
    }

    public static RegisterBrgyService getInstance(BarangayDao barangayDao){
        if(Holder.instance == null){
            Holder.instance = new RegisterBrgyService(barangayDao);
        }

        return Holder.instance;
    }

    protected BarangayBuilder getAdminBuilder(){
        return BuilderFactory.createBarangayBuilder();
    }

    protected void checkBrgyName(String brgyName){
        if(brgyName.length() < BarangayConfig.MIN_BARANGAY_NAME_LENGTH.getValue()){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("barangay name")
            );
        }
    }

    protected void checkMunicipal_prov(String municipal_prov, String type){
        if(!Validation.isInputMatchesLettersWithSpaces(municipal_prov)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_LETTERS_ONLY.getFormattedMessage(type)
            );
        }

        if(municipal_prov.length() < BarangayConfig.MIN_MUNICIPALITY_PROVINCE_LENGTH.getValue()){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage(type)
            );
        }
    }

    protected int getCheckInputBrgyPin(String strBrgyPin){
        if(!Validation.isInputMatchesNumbers(strBrgyPin)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NUM_ONLY.getFormattedMessage("barangay pin")
            );
        }

        if(strBrgyPin.length() < BarangayConfig.
                MIN_PIN_LENGTH
                .getValue()
        ){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("barangay pin")
            );
        }

        return Integer.parseInt(strBrgyPin);
    }

    protected int getBarangayId(Barangay barangay){
        int barangayId = barangayDao.getBarangayId(barangay);

        if(barangayId == 0){
            throw new NotFoundException(
                    InputMessageCollection.INPUT_OBJECT_NOT_EXIST.getFormattedMessage("barangay id")
            );
        }

        return barangayId;
    }

    protected void addBarangay(Barangay barangay){
        if(!barangayDao.addBarangay(barangay)){
            throw new OperationFailedException("Failed to add the barangay! Please try again.");
        }
    }
}
