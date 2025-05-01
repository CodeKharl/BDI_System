package org.isu_std.logsign.adminlogin;

import org.isu_std.dao.AdminDao;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.Admin;
import org.isu_std.io.collections.InputMessageCollection;

import java.util.Optional;

public class AdminLoginService{
    private final AdminDao adminDao;

    private AdminLoginService(AdminDao adminDao){
        this.adminDao = adminDao;
    }

    private static final class Holder{
        private static AdminLoginService instance;
    }

    public static AdminLoginService getInstance(AdminDao adminDao){
        if(Holder.instance == null){
            Holder.instance = new AdminLoginService(adminDao);
        }

        return Holder.instance;
    }

    protected Admin getAdmin(int adminId){
         Optional<Admin> optionalAdmin = adminDao.getOptionalAdmin(adminId);
         return optionalAdmin.orElseThrow(() -> new NotFoundException(
                         InputMessageCollection.INPUT_OBJECT_NOT_EXIST.getFormattedMessage("Admin")
                 )
         );
    }

    protected void checkAdminPin(int actualAdminPin, int inputPin){
        if (actualAdminPin != inputPin){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NOT_EQUAL.getFormattedMessage("admin pin")
            );
        }
    }
}
