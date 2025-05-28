package org.isu_std.user.user_acc_manage.user_personal.personalmodify;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.models.model_builders.UserBuilder;
import org.isu_std.models.model_builders.UserPersonalBuilder;
import org.isu_std.user.user_acc_manage.user_personal.ManagePersonalService;
import org.isu_std.user.user_acc_manage.user_personal.PersonalInfoSetter;

public class ModifyPersonalService {
    private final ManagePersonalService managePersonalService;
    private final UserPersonalDao userPersonalDao;

    public ModifyPersonalService(ManagePersonalService managePersonalService, UserPersonalDao userPersonalDao){
        this.managePersonalService = managePersonalService;
        this.userPersonalDao = userPersonalDao;
    }

    protected ModifyPersonalContext createPersonalModifierContext(User user, UserPersonalBuilder userPersonalBuilder){
        return new ModifyPersonalContext(user, userPersonalBuilder);
    }

    protected String[] getPersonalDetails(){
        return this.managePersonalService.getPersonalDetails();
    }

    protected String[] getPersonalDetailSpecs(){
        return this.managePersonalService.getPersonalDetailSpecs();
    }

    protected UserPersonalBuilder getUserPersonalBuilder(){
        return this.managePersonalService.getUserPersonalBuilder();
    }

    protected PersonalInfoSetter getPersonalInfoSetter(UserPersonalBuilder userPersonalBuilder){
        return this.managePersonalService.createPersonalInfoSetter(userPersonalBuilder);
    }

    protected void saveModifiedPersonalInfo(ModifyPersonalContext modifyPersonalContext){
        int userId = modifyPersonalContext.getUser().userId();
        String chosenDetail = modifyPersonalContext.getChosenDetail();
        UserPersonal userPersonal = modifyPersonalContext.getUserPersonalBuilder().build();

        try {
            if (!userPersonalDao.modifyUserPersonal(userId, chosenDetail, userPersonal)) {
                throw new OperationFailedException(
                        "Failed to modify personal information. Please try again."
                );
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to update user personal by : " + chosenDetail);
        }
    }
}
