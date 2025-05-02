package org.isu_std.user.user_acc_manage.userbarangay;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.Barangay;
import org.isu_std.models.User;

public class ManageBarangayController {
    private final ManageBarangayService manageBarangayService;
    private final User user;

    public ManageBarangayController(ManageBarangayService manageBarangayService, User user){
        this.manageBarangayService = manageBarangayService;
        this.user = user;
    }

    public void printBarangayInfo(){
        try{
            Barangay barangay = manageBarangayService.getBarangay(user.barangayId());
            barangay.printStats();
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }
    }
}
