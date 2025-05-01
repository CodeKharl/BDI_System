package org.isu_std;

import org.isu_std.models.Admin;
import org.isu_std.models.User;

public class ClientContext {
    private User user;
    private Admin admin;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
