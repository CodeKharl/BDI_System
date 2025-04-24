package org.isu_std.user.user_acc_manage.userpersonal;

import org.isu_std.io.exception.NotFoundException;
import org.isu_std.models.UserPersonal;
import org.isu_std.io.Util;

public class ManagePersonalController {
    private final ManagePersonalService managePersonalService;
    private final int userId;

    private UserPersonal userPersonal;

    public ManagePersonalController(ManagePersonalService managePersonalService, int userId){
        this.managePersonalService = managePersonalService;
        this.userId = userId;
    }

    protected boolean setUserPersonal(){
        try{
            this.userPersonal = managePersonalService.getUserPersonal(userId);
            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printExistingInfos(){
        userPersonal.printPersonalStats();
    }

    protected void userPersonalCreation(){
        managePersonalService.createPersonal(userId).createPerformed();
    }
}
