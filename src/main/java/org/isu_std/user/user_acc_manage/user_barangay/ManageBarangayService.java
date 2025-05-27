package org.isu_std.user.user_acc_manage.user_barangay;

import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Barangay;
import org.isu_std.models.User;
import org.isu_std.models.model_builders.BarangayBuilder;
import org.isu_std.models.model_builders.BuilderFactory;
import org.isu_std.user_brgy_select.BarangaySelect;
import org.isu_std.user_brgy_select.BrgySelectFactory;

import java.io.IOException;
import java.util.Optional;

public class ManageBarangayService {
    private final BarangayDao barangayDao;
    private final UserDao userDao;

    public ManageBarangayService(BarangayDao barangayDao, UserDao userDao){
        this.barangayDao = barangayDao;
        this.userDao = userDao;
    }

    protected BarangaySelect getBrgySelect(){
        return BrgySelectFactory.createBrgySelect(barangayDao);
    }

    protected BarangayBuilder getBarangayBuilder(){
        return BuilderFactory.createBarangayBuilder();
    }

    protected Barangay getBarangay(int barangayId){
        try {
            Optional<Barangay> optionalBarangay = barangayDao.findOptionalBarangay(barangayId);

            return optionalBarangay.orElseThrow(
                    () -> new NotFoundException("Barangay Information not found!")
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch barangay by barangay_id : " + barangayId);
        }
    }

    protected void updateBrgyPerform(User newUser) throws OperationFailedException{
        try {
            if (!userDao.updateUserBarangay(newUser)) {
                throw new OperationFailedException(
                        "Failed to change your barangay! Please try again."
                );
            }
        }catch (ServiceException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to update barangay with barangay_id : " + newUser.barangayId());
        }
    }

    protected User createNewUser(User user, Barangay newBarangay){
        return new User(
                user.userId(),
                user.username(),
                user.password(),
                newBarangay.barangayId()
        );
    }
}
