package org.isu_std.models.modelbuilders;

import org.isu_std.models.User;

public class UserBuilder {
    private int userId;
    private String username;
    private String password;
    private int barangayId;

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

    public User build(){
        return new User(
                this.userId,
                this.username,
                this.password,
                this.barangayId
        );
    }

    public int getBarangayId(){
        return this.barangayId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void resetAllValues(){
        this.userId = 0;
        this.username = null;
        this.password = null;
        this.barangayId = 0;
    }
}
