package org.isu_std.user.user_acc_manage.user_barangay;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Barangay;
import org.isu_std.models.User;
import org.isu_std.models.modelbuilders.BarangayBuilder;

public class ManageBarangayController {
    private final ManageBarangayService manageBarangayService;
    private final BarangayBuilder newBrgyBuilder;

    private User user;

    public ManageBarangayController(ManageBarangayService manageBarangayService, User user){
        this.manageBarangayService = manageBarangayService;
        this.user = user;

        this.newBrgyBuilder = manageBarangayService.getBarangayBuilder();
    }

    protected User getUser(){
        return this.user;
    }

    protected void printExistingBrgyInfo(){
        try{
            Barangay barangay = manageBarangayService.getBarangay(user.barangayId());
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
            manageBarangayService.changeBrgyPerform(user, barangay);
            this.user = manageBarangayService.createNewUser(user, barangay);
            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
