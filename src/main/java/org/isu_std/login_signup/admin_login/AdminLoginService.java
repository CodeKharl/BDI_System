package org.isu_std.login_signup.admin_login;

import org.isu_std.dao.AdminDao;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.Admin;
import org.isu_std.io.collections.InputMessageCollection;

import java.util.Optional;

public class AdminLoginService{
    private final AdminDao adminDao;

    public AdminLoginService(AdminDao adminDao){
        this.adminDao = adminDao;
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
