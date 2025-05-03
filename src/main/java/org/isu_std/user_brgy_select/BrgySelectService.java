package org.isu_std.user_brgy_select;

import org.isu_std.dao.BarangayDao;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.Barangay;
import org.isu_std.models.modelbuilders.BarangayBuilder;
import org.isu_std.models.modelbuilders.BuilderFactory;

public class BrgySelectService {
    private final BarangayDao barangayDao;

    protected BrgySelectService(BarangayDao barangayDao){
        this.barangayDao = barangayDao;
    }

    protected BarangayBuilder getBarangayBuilder(){
        return BuilderFactory.createBarangayBuilder();
    }

    public int getUserBarangayId(Barangay barangay){
        int barangayId = barangayDao.getBarangayId(barangay);

        if(barangayId == 0){
            throw new NotFoundException("The Barangay you entered is not exist! Please try again.");
        }

        return barangayId;
    }
}
