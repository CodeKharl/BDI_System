package org.isu_std.user_brgy_select;

import org.isu_std.dao.BarangayDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Barangay;
import org.isu_std.models.model_builders.BarangayBuilder;
import org.isu_std.models.model_builders.BuilderFactory;

import java.util.Optional;

public class BrgySelectService {
    private final BarangayDao barangayDao;

    protected BrgySelectService(BarangayDao barangayDao){
        this.barangayDao = barangayDao;
    }

    protected BarangayBuilder getBarangayBuilder(){
        return BuilderFactory.createBarangayBuilder();
    }

    public int getUserBarangayId(Barangay barangay){
        try {
            Optional<Integer> optionalBrgyId = barangayDao.findBarangayId(barangay);

            return optionalBrgyId.orElseThrow(
                    () -> new NotFoundException("The barangay you entered is not exist! Please try again.")
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch user barangay ID by barangay : " + barangay);
        }
    }
}
