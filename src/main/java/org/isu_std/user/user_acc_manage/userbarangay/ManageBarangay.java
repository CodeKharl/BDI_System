package org.isu_std.user.user_acc_manage.userbarangay;

import org.isu_std.io.Util;
import org.isu_std.user.user_acc_manage.UserManageProcess;

public class ManageBarangay implements UserManageProcess {
    private final ManageBarangayController manageBarangayController;

    public ManageBarangay(ManageBarangayController manageBarangayController){
        this.manageBarangayController = manageBarangayController;
    }

    @Override
    public void managePerformed(String manageTitle){
        Util.printSectionTitle(manageTitle);
    }
}
