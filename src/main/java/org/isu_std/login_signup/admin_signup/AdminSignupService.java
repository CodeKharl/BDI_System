package org.isu_std.login_signup.admin_signup;

import org.isu_std.admin_info_manager.AdminInfoManager;
import org.isu_std.admin_info_manager.AdminInfoManagerFactory;
import org.isu_std.dao.AdminDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.io.dynamic_enum_handler.EnumValueProvider;
import org.isu_std.models.Admin;
import org.isu_std.models.model_builders.AdminBuilder;
import org.isu_std.models.model_builders.BuilderFactory;

import java.util.Optional;

public class AdminSignupService {
    private final AdminDao adminDao;
    private final AdminInfoManager adminInfoManager;

    public AdminSignupService(AdminDao adminDao){
        this.adminDao = adminDao;
        this.adminInfoManager = AdminInfoManagerFactory.create(adminDao);
    }

    protected AdminBuilder createAdminBuilder(){
        return BuilderFactory.createAdminBuilder();
    }

    protected String[] getAdminAttributesWithSpecs(){
        return adminInfoManager.getAdminAttributesWithSpecs();
    }

    protected void checkAdminName(String adminName){
        adminInfoManager.checkAdminName(adminName);
    }

    protected void checkAdminPin(String adminPin){
        adminInfoManager.checkAdminPin(adminPin);
    }

    protected void insertAdminPerform(Admin admin){
        try {
            if (!adminDao.insertAdmin(admin)) {
                throw new OperationFailedException("Failed to create the admin account.");
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to insert admin : " + admin);
        }
    }

    protected int getAdminId(String adminName){
        Optional<Integer> optionalAdminId = adminInfoManager.fetchAdminId(adminName);

        return optionalAdminId.orElseThrow(
                () -> new NotFoundException("Theres no existing admin_id for ")
        );
    }
}
