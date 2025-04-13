package org.isu_std.client_manager;

import org.isu_std.models.Admin;

public class AdminManager {
    private Admin admin;

    protected AdminManager(){}

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
