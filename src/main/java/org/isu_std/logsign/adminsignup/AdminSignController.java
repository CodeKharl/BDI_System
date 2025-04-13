package org.isu_std.logsign.adminsignup;

import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.modelbuilders.AdminBuilder;

public class AdminSignController {
    private final String[] INFORMATION_ATTRIBUTES = {
            "Admin Name (Min. %d)".formatted(AdminSignService.getMinNameLength()),
            "Pin (Min. %d)".formatted(AdminSignService.getMinPinLength())
    };

    private final AdminSignService adminSignService;
    private final AdminBuilder adminBuilder;

    public AdminSignController(AdminSignService adminSignService){
        this.adminSignService = adminSignService;
        this.adminBuilder = adminSignService.createAdminBuilder();
    }

    protected boolean isInputAccepted(String input, int count){
        try {
            switch (count) {
                case 0 -> {
                    adminSignService.checkAdminName(input);
                    adminSignService.checkAdminIdExist(input);
                    adminBuilder.adminName(input);
                }

                case 1 -> {
                    adminSignService.checkAdminPin(input);
                    adminBuilder.adminPin(Integer.parseInt(input));
                }
            }

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean adminSignupProcessComplete(){
        try {
            adminSignService.insertingAdminData(adminBuilder.build());
            Util.printMessage("You successfully created your admin account!");
            Util.printMessage(
                    "Your ADMIN ID : " + adminSignService.getAdminId(adminBuilder.getAdminName())
            );

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected String[] getInfoAttributes(){
        return this.INFORMATION_ATTRIBUTES;
    }
}
