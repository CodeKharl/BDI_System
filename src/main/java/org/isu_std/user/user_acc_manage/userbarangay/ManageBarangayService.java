package org.isu_std.user.user_acc_manage.userbarangay;

import org.isu_std.dao.BarangayDao;

public class ManageBarangayService {
    private final BarangayDao barangayDao;

    public ManageBarangayService(BarangayDao barangayDao){
        this.barangayDao = barangayDao;
    }
}
