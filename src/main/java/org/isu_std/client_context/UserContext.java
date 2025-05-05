package org.isu_std.client_context;

import org.isu_std.models.User;

public class UserContext {
    private User user;

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public String getUsername(){
        return this.user.username();
    }

    public int getUserId(){
        return this.user.userId();
    }
}
