package org.isu_std.io.file_setup;

import org.isu_std.io.SystemLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class FileManager {
    private FileManager(){}

    public static void deleteFile(Path path){
        try{
            Files.delete(path);
        }catch(NoSuchFileException _){
        }catch (IOException e){
            SystemLogger.logWarning(FileManager.class, e.getMessage());
        }
    }
}
