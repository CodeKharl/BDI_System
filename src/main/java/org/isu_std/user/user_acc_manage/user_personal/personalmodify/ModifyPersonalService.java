package org.isu_std.user.user_acc_manage.user_personal.personalmodify;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.models.modelbuilders.UserPersonalBuilder;

public class ModifyPersonalService {
    private final UserPersonalDao userPersonalDao;

    public ModifyPersonalService(UserPersonalDao userPersonalDao){
        this.userPersonalDao = userPersonalDao;
    }

    public ModifyPersonalContext createPersonalModifierManager(User user, UserPersonalBuilder userPersonalBuilder){
        return new ModifyPersonalContext(user, userPersonalBuilder);
    }

    public void saveModifiedPersonalInfo(ModifyPersonalContext modifyPersonalContext){
        int userId = modifyPersonalContext.getUser().userId();
        String chosenDetail = modifyPersonalContext.getChosenDetail();
        UserPersonal userPersonal = modifyPersonalContext.getUserPersonalBuilder().build();

        if(!userPersonalDao.modifyUserPersonal(userId, chosenDetail, userPersonal)){
            throw new OperationFailedException(
                    "Failed to modify personal information. Please try again."
            );
        }
    }
}
