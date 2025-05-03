package org.isu_std.user.user_acc_manage.userbarangay;

import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Barangay;
import org.isu_std.models.User;
import org.isu_std.models.modelbuilders.BarangayBuilder;
import org.isu_std.models.modelbuilders.BuilderFactory;
import org.isu_std.user_brgy_select.BrgySelect;
import org.isu_std.user_brgy_select.BrgySelectFactory;

import java.util.Optional;

public class ManageBarangayService {
    private final BarangayDao barangayDao;
    private final UserDao userDao;

    public ManageBarangayService(BarangayDao barangayDao, UserDao userDao){
        this.barangayDao = barangayDao;
        this.userDao = userDao;
    }

    protected BrgySelect getBrgySelect(){
        return BrgySelectFactory.createBrgySelect(barangayDao);
    }

    protected BarangayBuilder getBarangayBuilder(){
        return BuilderFactory.createBarangayBuilder();
    }

    protected Barangay getBarangay(int barangayId){
        Optional<Barangay> optionalBarangay = barangayDao.getOptionalBarangay(barangayId);

        return optionalBarangay.orElseThrow(
            () -> new NotFoundException("Barangay Information not found!")
        );
    }

    protected void changeBrgyPerformed(User user, Barangay newBarangay){
        if(!userDao.updateUserBarangay(user, newBarangay)){
            throw new OperationFailedException("Failed to change your barangay! Please try again.");
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
