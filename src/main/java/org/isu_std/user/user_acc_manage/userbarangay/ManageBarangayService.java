package org.isu_std.user.user_acc_manage.userbarangay;

import org.isu_std.dao.BarangayDao;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.Barangay;

import java.util.Optional;

public class ManageBarangayService {
    private final BarangayDao barangayDao;

    public ManageBarangayService(BarangayDao barangayDao){
        this.barangayDao = barangayDao;
    }

    protected Barangay getBarangay(int barangayId){
        Optional<Barangay> optionalBarangay = barangayDao.getOptionalBarangay(barangayId);

        return optionalBarangay.orElseThrow(
            () -> new NotFoundException("Barangay Information not found!")
        );
    }
}
