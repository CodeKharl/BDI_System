package org.isu_std;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

// Functional Paradigm. No structure.
// Use for getting the type of client.

public class ClientManager {
    public final static int ADMIN_VAL = 1;
    public final static int USER_VAL = 2;

    protected static int getClientType(){
        // Returns whether the person who uses the system is an admin or a user.

        Util.printSectionTitle("TYPE SELECTION");
        Util.printQuestion(
                "Are you an (%d)Admin or (%d)User ?\n"
                        .formatted(ADMIN_VAL, USER_VAL)
        );

        while(true){
            int typeChoice = SystemInput.getIntInput(
                    "Enter the number of your type : "
            );

            switch (typeChoice){
                case ADMIN_VAL, USER_VAL -> {
                    return typeChoice;
                }
                default -> Util.printInputInvalidMessage("type choice");
            }
        }
    }
}
