package org.isu_std.io.folder_setup;

import org.isu_std.io.SystemLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FolderManager {
    public static void setFileFolder(String filePath){
        if(new File(filePath).isDirectory()){
            return;
        }

        try{
            Files.createDirectories(Path.of(filePath));
        }catch (IOException e){
            SystemLogger.logWarning(FolderManager.class, e.getMessage());
        }
    }
}
