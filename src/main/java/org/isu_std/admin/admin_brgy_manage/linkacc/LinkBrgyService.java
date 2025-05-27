package org.isu_std.admin.admin_brgy_manage.linkacc;

import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Admin;
import org.isu_std.models.model_builders.AdminBuilder;
import org.isu_std.models.Barangay;
import org.isu_std.dao.AdminDao;
import org.isu_std.dao.BarangayDao;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.models.model_builders.BuilderFactory;

import java.util.Optional;

public class LinkBrgyService {
    private final BarangayDao barangayDao;
    private final AdminDao adminDao;

    public LinkBrgyService(BarangayDao barangayDao, AdminDao adminDao){
        this.barangayDao = barangayDao;
        this.adminDao = adminDao;
    }

    protected Admin buildAdminWithId(Admin prevAdmin, Barangay barangay){
        AdminBuilder adminBuilder = BuilderFactory.createAdminBuilder();
        adminBuilder.adminId(prevAdmin.adminId())
                .adminName(prevAdmin.adminName())
                .adminPin(prevAdmin.adminPin())
                .barangayId(barangay.barangayId());

        return adminBuilder.build();
    }

    protected Barangay getBarangay(int barangayId){
        try {
            Optional<Barangay> optionalBarangay = barangayDao.findOptionalBarangay(barangayId);

            return optionalBarangay.orElseThrow(
                    () -> new NotFoundException(
                            InputMessageCollection.INPUT_OBJECT_NOT_EXIST.getFormattedMessage("Barangay")
                    )
            );

        }catch(DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException(
                    "Failed to retrieve barangay with barangay ID : " + barangayId
            );
        }
    }

    protected void checkBarangayPin(int actualBrgyPin, int brgyInputPin){
        if(actualBrgyPin != brgyInputPin){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NOT_EQUAL.getFormattedMessage("barangay pin")
            );
        }
    }

    protected void setAdminBarangayId(int barangayId, int adminId) throws OperationFailedException {
        try {
            if (!adminDao.updateAdminBrgyId(barangayId, adminId)) {
                throw new OperationFailedException(
                        "Failed to link your account to the barangay! Please try again."
                );
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException(
                    "Failed to update barangay ID with new barangay ID : " + barangayId
            );
        }
    }
}
