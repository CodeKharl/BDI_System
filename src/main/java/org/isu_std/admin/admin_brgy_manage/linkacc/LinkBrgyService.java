package org.isu_std.admin.admin_brgy_manage.linkacc;

import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Admin;
import org.isu_std.models.modelbuilders.AdminBuilder;
import org.isu_std.models.Barangay;
import org.isu_std.dao.AdminDao;
import org.isu_std.dao.BarangayDao;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.models.modelbuilders.BuilderFactory;

import java.util.Optional;

public class LinkBrgyService {
    private final BarangayDao barangayDao;
    private final AdminDao adminDao;

    private LinkBrgyService(BarangayDao barangayDao, AdminDao adminDao){
        this.barangayDao = barangayDao;
        this.adminDao = adminDao;
    }

    private static final class Holder{
        private static LinkBrgyService instance;
    }

    public static LinkBrgyService getInstance(BarangayDao barangayDao, AdminDao adminDao){
        if(Holder.instance == null){
            Holder.instance = new LinkBrgyService(barangayDao, adminDao);
        }

        return Holder.instance;
    }

    protected Admin buildAdminWithId(Admin prevAdmin, int adminId){
        AdminBuilder adminBuilder = BuilderFactory.createAdminBuilder();
        adminBuilder.adminId(prevAdmin.adminId())
                .adminName(prevAdmin.adminName())
                .adminPin(prevAdmin.adminPin())
                .adminId(adminId);

        return adminBuilder.build();
    }

    protected Barangay getBarangay(int barangayId){
        Optional<Barangay> optionalBarangay = barangayDao.getOptionalBarangay(barangayId);

        return optionalBarangay.orElseThrow(
                () -> new NotFoundException(
                        InputMessageCollection.INPUT_OBJECT_NOT_EXIST.getFormattedMessage("Barangay")
                )
        );
    }

    protected void checkBarangayPin(int actualBrgyPin, int brgyInputPin){
        if(actualBrgyPin != brgyInputPin){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NOT_EQUAL.getFormattedMessage("barangay pin")
            );
        }
    }

    protected void setAdminBarangayId(int barangayId, int adminId){
        if(!adminDao.setAdminBarangayId(barangayId, adminId)){
            throw new OperationFailedException(
                    "Failed to link your account to the barangay! Please try again."
            );
        }
    }
}
