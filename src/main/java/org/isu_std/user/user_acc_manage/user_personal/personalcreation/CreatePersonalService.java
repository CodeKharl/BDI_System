package org.isu_std.user.user_acc_manage.user_personal.personalcreation;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.UserPersonal;

public class CreatePersonalService {
    private final UserPersonalDao userPersonalDao;

    public CreatePersonalService(UserPersonalDao userPersonalDao){
        this.userPersonalDao = userPersonalDao;
    }

    public void savePersonalInfo(int userId, UserPersonal userPersonal){
        if(!userPersonalDao.addUserPersonal(userId, userPersonal)){
            throw new OperationFailedException(
                    "Failed to save your personal information! Please try again!"
            );
        }
    }
}
