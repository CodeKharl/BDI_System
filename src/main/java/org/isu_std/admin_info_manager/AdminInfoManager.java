package org.isu_std.admin_info_manager;

import org.isu_std.dao.AdminDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.Validation;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.io.dynamic_enum_handler.EnumValueProvider;

import java.util.Optional;

public class AdminInfoManager {
    private final AdminDao adminDao;

    protected AdminInfoManager(AdminDao adminDao){
        this.adminDao = adminDao;
    }

    public String[] getAdminAttrNamesWithSpecs(){
        String[] attributes = getAdminAttributeNames();
        String[] specs = getAdminAttributeSpecs();

        for(int i = 0; i < attributes.length; i++){
            attributes[i] = "%s (%s)".formatted(attributes[i], specs[i]);
        }

        return attributes;
    }

    public String[] getAdminAttributeNames(){
        return EnumValueProvider.getStringArrValue(
                AdminInfoConfig.INFO_ATTRIBUTE_NAMES.getConfigValue()
        );
    }

    public String[] getAdminAttributeSpecs(){
        return EnumValueProvider.getStringArrValue(
                AdminInfoConfig.INFO_ATTRIBUTE_SPECIFICATIONS.getConfigValue()
        );
    }

    public void checkAdminName(String adminName){
        // Checks whether the admin name that inputs is accepted or not.
        int minNameLength = EnumValueProvider.getIntValue(
                AdminInfoConfig.MIN_NAME_LENGTH.getConfigValue()
        );

        if(!Validation.isInputLengthAccepted(minNameLength, adminName)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("admin name")
            );
        }

        checkAdminIdExist(adminName);
    }

    private void checkAdminIdExist(String adminName){
        Optional<Integer> optionalAdminId = fetchAdminId(adminName);

        if (optionalAdminId.isPresent()) {
            throw new IllegalArgumentException(
                    "The username (%s) is already exists! Please enter a unique name."
                            .formatted(adminName)
            );
        }
    }

    public void checkAdminPin(String strPin){
        int minPinLength = EnumValueProvider.getIntValue(
                AdminInfoConfig.MIN_PIN_LENGTH.getConfigValue()
        );

        if(!Validation.isInputLengthAccepted(minPinLength, strPin)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("pin")
            );
        }

        if(!Validation.isInputMatchesNumbers(strPin)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NOT_EQUAL.getFormattedMessage("pin")
            );
        }
    }

    public Optional<Integer> fetchAdminId(String adminName){
        try {
            return this.adminDao.findAdminIDByName(adminName);
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch admin ID with admin_name : " + adminName);
        }
    }
}
