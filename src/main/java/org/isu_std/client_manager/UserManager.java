package org.isu_std.client_manager;

import org.isu_std.models.User;

// Class that temporarily stored the data of client while they're using the system.

public class UserManager {
    private User user;

    protected UserManager(){}

    public void setUser(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
