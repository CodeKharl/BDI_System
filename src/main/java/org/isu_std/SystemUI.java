package org.isu_std;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

/*
   This class is the Front-End of the system.
   It is a console based - program which the user or admin interacts with.
*/

public final class SystemUI{
    private final String[] CHOICES = {"Login", "Sign-in", "About the System", "Exit"};

    private final SystemController systemController;

    public SystemUI(SystemController systemController){
        this.systemController = systemController;
    }

    public void start(){
        // This is the startup section of the system.
        while(true){
            Util.printSectionTitle("Barangay Documents Issuance System (BDIS)");
            Util.printChoices(CHOICES);

            int choice = SystemInput.getIntChoice("Enter your choice : ", CHOICES.length);

            if(!systemController.isSystemRunning(choice)){
                return;
            }
        }
    }
}