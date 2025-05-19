package org.isu_std.io;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

// Class that gives logger through the system.
// This will give a better message for better debugging errors.

public class SystemLogger {
    private final static String LOG_FILE = "error.log";

    private SystemLogger(){}

    public static void log(String message, Exception e){
        try(FileWriter fileWriter = new FileWriter(LOG_FILE, true);
            PrintWriter out = new PrintWriter(fileWriter)
        ){
            out.println("ERROR : " + message);

            if(e != null){
                e.printStackTrace(out);
            }
        }catch (IOException ioe){
            Util.printMessage("Failed to write to log file : " + ioe.getMessage());
        }
    }
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
