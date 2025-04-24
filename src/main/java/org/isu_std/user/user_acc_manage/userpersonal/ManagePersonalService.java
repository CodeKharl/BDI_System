package org.isu_std.user.user_acc_manage.userpersonal;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_acc_manage.userpersonal.personalcreation.CreatePersonal;
import org.isu_std.user.user_acc_manage.userpersonal.personalcreation.CreatePersonalController;
import org.isu_std.user.user_acc_manage.userpersonal.personalcreation.CreatePersonalService;

import java.util.Optional;

public class ManagePersonalService {
    private final UserPersonalDao userPersonalDao;

    public ManagePersonalService(UserPersonalDao userPersonalDao){
        this.userPersonalDao = userPersonalDao;
    }

    protected UserPersonal getUserPersonal(int userId){
        Optional<UserPersonal> userPersonal = userPersonalDao.getOptionalUserPersonal(userId);
        return userPersonal.orElseThrow(
                () -> new NotFoundException("Theres no existing personal information of your account.")
        );
    }

    protected CreatePersonal createPersonal(int userId){
        return new CreatePersonal(
                new CreatePersonalController(
                        new CreatePersonalService(userPersonalDao),
                        userId
                )
        );
    }
}
