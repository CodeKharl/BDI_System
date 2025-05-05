package org.isu_std.models;

import org.isu_std.io.Util;

public record User(int userId, String username, String password, int barangayId){

    public void printUserDetails(){
        // Methods that only display the user details that the user only knows.

        Util.printSectionTitle("User Info (Username - Password(*))");

        String asteriskPass = "*".repeat(password.length());
        Util.printInformation("%s - %s".formatted(username, asteriskPass));
    }
}
