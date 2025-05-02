package org.isu_std.user.user_acc_manage.userpersonal;

import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.io.Util;

public class ManagePersonalController {
    private final ManagePersonalService managePersonalService;
    private final User user;

    private UserPersonal userPersonal;

    public ManagePersonalController(ManagePersonalService managePersonalService, User user){
        this.managePersonalService = managePersonalService;
        this.user = user;
    }

    protected boolean setUserPersonal(){
        try{
            this.userPersonal = managePersonalService.getUserPersonal(user.userId());
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
        managePersonalService.createPersonal(user).createPerformed();
    }

    protected void userPersonalModification(){
        managePersonalService.createModifyPersonal(user).modifyPerformed();
    }
}
