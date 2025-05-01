package org.isu_std.logsign.adminsignup;

import org.isu_std.dao.AdminDao;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Admin;
import org.isu_std.models.modelbuilders.AdminBuilder;
import org.isu_std.models.modelbuilders.BuilderFactory;

public class AdminSignService{
    private final static int MIN_NAME_LENGTH = 8;
    private final static int MIN_PIN_LENGTH = 4;

    private final AdminDao adminRepository;

    private AdminSignService(AdminDao adminRepository){
        this.adminRepository = adminRepository;
    }

    private static final class Holder{
        private static AdminSignService instance;
    }

    public static AdminSignService getInstance(AdminDao adminRepository){
        if(Holder.instance == null){
            Holder.instance = new AdminSignService(adminRepository);
        }

        return Holder.instance;
    }

    protected AdminBuilder createAdminBuilder(){
        return BuilderFactory.createAdminBuilder();
    }

    public void checkAdminName(String adminName){
        // Checks whether the admin name that inputs is accepted or not.
        if(!Validation.isInputLengthAccepted(MIN_NAME_LENGTH,adminName)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("admin name")
            );
        }
    }

    public void checkAdminPin(String strPin){
        if(!Validation.isInputLengthAccepted(MIN_PIN_LENGTH, strPin)){
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

    public void insertingAdminData(Admin admin){
        if(adminRepository.insertAdmin(admin)){
            return;
        }

        throw new OperationFailedException("Failed to create the admin account.");
    }

    public void checkAdminIdExist(String adminName){
        // Values can be 0 or the id of admin.
        if(getAdminId(adminName) != 0){
            throw new IllegalArgumentException(
                    "The username (%s) is already exists! Please enter a unique name.".formatted(adminName)
            );
        }
    }

    public int getAdminId(String adminName){
        return adminRepository.getAdminID(adminName);
    }

    public static int getMinNameLength(){
        return MIN_NAME_LENGTH;
    }

    public static int getMinPinLength(){
        return MIN_PIN_LENGTH;
    }
}
