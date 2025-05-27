package org.isu_std.user.user_acc_manage.user_personal;

import org.isu_std.client_context.UserContext;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.io.Util;

public class ManagePersonalController {
    private final ManagePersonalService managePersonalService;
    private final UserContext userContext;

    private UserPersonal userPersonal;

    public ManagePersonalController(ManagePersonalService managePersonalService, UserContext userContext){
        this.managePersonalService = managePersonalService;
        this.userContext = userContext;
    }

    protected boolean setUserPersonal(){
        try{
            int userId = userContext.getUser().userId();
            this.userPersonal = managePersonalService.getUserPersonal(userId);

            return true;
        }catch (ServiceException | NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printExistingInfos(){
        userPersonal.printPersonalStats();
    }

    protected void userPersonalCreation(){
        managePersonalService
                .createPersonal(userContext.getUser())
                .createPerformed();
    }

    protected void userPersonalModification(){
        managePersonalService
                .createModifyPersonal(userContext.getUser())
                .modifyPerformed();
    }
}
