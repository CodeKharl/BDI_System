package org.isu_std.admin.admin_brgy_manage.registeracc;

import org.isu_std.admin.admin_brgy_manage.BarangayConfig;
import org.isu_std.dao.BarangayDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Barangay;
import org.isu_std.models.model_builders.BarangayBuilder;
import org.isu_std.models.model_builders.BuilderFactory;

import java.util.Optional;

public class RegisterBrgyService {
    private final BarangayDao barangayDao;

    public RegisterBrgyService(BarangayDao barangayDao){
        this.barangayDao = barangayDao;
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

    protected void checkBarangayIfUnique(Barangay barangay){
        try {
            Optional<Integer> optionalBarangayId = barangayDao.findBarangayId(barangay);

            if (optionalBarangayId.isPresent()) {
                throw new IllegalArgumentException(
                        "Barangay already exist! Please enter a unique brgy address."
                );
            }
        }catch(DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw barangayIdDataException(barangay);
        }
    }

    protected int getNewBarangayId(Barangay barangay){
        try {
            Optional<Integer> barangayId = barangayDao.findBarangayId(barangay);

            return barangayId.orElseThrow(
                    () -> new NotFoundException(
                            "BarangayID not found! There some issue on the system, please try when it fixes"
                    )
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw barangayIdDataException(barangay);
        }
    }

    protected ServiceException barangayIdDataException(Barangay barangay){
        return new ServiceException("Failed to fetch barangay id with barangay : " + barangay);
    }

    protected void addBarangay(Barangay barangay){
        try {
            if (!barangayDao.insertBarangay(barangay)) {
                throw new OperationFailedException("Failed to add the barangay! Please try again.");
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to insert barangay : " + barangay);
        }
    }
}
