package org.isu_std.user.user_acc_manage.userpersonal.personalmodify;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.UserPersonal;
import org.isu_std.models.modelbuilders.UserPersonalBuilder;
import org.isu_std.user.user_acc_manage.userpersonal.ManagePersonalService;

public class ModifyPersonalService {
    private final UserPersonalDao userPersonalDao;

    public ModifyPersonalService(UserPersonalDao userPersonalDao){
        this.userPersonalDao = userPersonalDao;
    }

    public ModifyPersonalContext createPersonalModifierManager(int userId, UserPersonalBuilder userPersonalBuilder){
        return new ModifyPersonalContext(userId, userPersonalBuilder);
    }

    public void saveModifiedPersonalInfo(ModifyPersonalContext modifyPersonalContext){
        int userId = modifyPersonalContext.getUserId();
        String chosenDetail = modifyPersonalContext.getChosenDetail();
        UserPersonal userPersonal = modifyPersonalContext.getUserPersonalBuilder().build();

        if(!userPersonalDao.modifyUserPersonal(userId, chosenDetail, userPersonal)){
            throw new OperationFailedException(
                    "Failed to modify personal information. Please try again."
            );
        }
    }
}
