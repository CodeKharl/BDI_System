package org.isu_std.io;

import java.io.IOException;

public class WindowsCLIClear {
    public static void cls(){
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "cls");
            processBuilder.inheritIO().start().waitFor();
        }catch (InterruptedException | IOException e){
            SystemLogger.logWarning(WindowsCLIClear.class, e.getMessage());
        }
    }
}
