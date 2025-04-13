package org.isu_std.models.modelbuilders;

import org.isu_std.models.User;

public class UserBuilder {
    private int userId;
    private String username;
    private String password;
    private int barangayId;
    private double cash;

    public UserBuilder userId(int userId){
        this.userId = userId;
        return this;
    }

    public UserBuilder username(String username){
        this.username = username;
        return this;
    }

    public UserBuilder password(String password){
        this.password = password;
        return this;
    }

    public UserBuilder barangayId(int barangayId){
        this.barangayId = barangayId;
        return this;
    }

    public UserBuilder cash(double cash){
        this.cash = cash;
        return this;
    }

    public User build(){
        return new User(
                this.userId,
                this.username,
                this.password,
                this.barangayId,
                this.cash
        );
    }
}
