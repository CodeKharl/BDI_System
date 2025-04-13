package org.isu_std.io;


import java.util.logging.Level;
import java.util.logging.Logger;

// Class that gives logger through the system.
// This will give a better message for better debugging errors.

public class SystemLogger {
    private SystemLogger(){}

    public static void logException(Class<?> clazz, String message, Exception e){
        Logger.getLogger(clazz.getName()).log(Level.SEVERE, message, e);
    }

    public static void logInfo(Class<?> clazz, String message){
        Logger.getLogger(clazz.getName()).log(Level.INFO, message);
    }

    public static void logWarning(Class<?> clazz, String message){
        Logger.getLogger(clazz.getName()).log(Level.WARNING, message);
    }
}
