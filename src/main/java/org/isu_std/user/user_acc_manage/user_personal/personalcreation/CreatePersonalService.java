package org.isu_std.user.user_acc_manage.user_personal.personalcreation;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.UserPersonal;
import org.isu_std.models.model_builders.UserPersonalBuilder;
import org.isu_std.user.user_acc_manage.user_personal.ManagePersonalService;
import org.isu_std.user.user_acc_manage.user_personal.PersonalInfoSetter;

public class CreatePersonalService {
    private final ManagePersonalService managePersonalService;
    private final UserPersonalDao userPersonalDao;

    public CreatePersonalService(ManagePersonalService managePersonalService, UserPersonalDao userPersonalDao){
        this.managePersonalService = managePersonalService;
        this.userPersonalDao = userPersonalDao;
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

    protected void savePersonalInfoPerform(int userId, UserPersonal userPersonal) throws OperationFailedException{
        try {
            if (!userPersonalDao.addUserPersonal(userId, userPersonal)) {
                throw new OperationFailedException(
                        "Failed to save your personal information! Please try again!"
                );
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to insert user personal info : " + userPersonal);
        }
    }
}
