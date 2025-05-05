package org.isu_std.user.user_acc_manage.user_barangay;

import org.isu_std.client_context.UserContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Barangay;
import org.isu_std.models.User;
import org.isu_std.models.model_builders.BarangayBuilder;

public class ManageBarangayController {
    private final ManageBarangayService manageBarangayService;
    private final BarangayBuilder newBrgyBuilder;

    private final UserContext userContext;

    public ManageBarangayController(ManageBarangayService manageBarangayService, UserContext userContext){
        this.manageBarangayService = manageBarangayService;
        this.userContext = userContext;
        this.newBrgyBuilder = manageBarangayService.getBarangayBuilder();
    }

    protected void printExistingBrgyInfo(){
        try{
            int barangayId = userContext.getUser().barangayId();
            Barangay barangay = manageBarangayService.getBarangay(barangayId);
            barangay.printFullBrgyNameOnly();
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }
    }

    protected boolean setChosenBarangay(){
        int chosenBrgyId = manageBarangayService.getBrgySelect().getBarangayID();

        if(chosenBrgyId != 0){
            newBrgyBuilder.barangayId(chosenBrgyId);
            return true;
        }

        return false;
    }

    protected boolean isChangingBrgySuccess(){
        try{
            Barangay barangay = newBrgyBuilder.build();
            User user = userContext.getUser();
            User newUser = manageBarangayService.createNewUser(user, barangay);

            // Update on database
            manageBarangayService.changeBrgyPerform(newUser);

            // Update on Object
            this.userContext.setUser(newUser);

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
