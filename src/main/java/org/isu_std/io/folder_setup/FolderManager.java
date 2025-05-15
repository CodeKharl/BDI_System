package org.isu_std.io.folder_setup;

import org.isu_std.io.SystemLogger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FolderManager {
    public static void setFileDirectory(FolderConfig folderConfig){
        Path path = Path.of(folderConfig.getDirectory());
        setFileDirectory(path);
    }

    public static void setFileDirectory(Path directory){
        try{
            Files.createDirectories(directory);
        }catch (FileAlreadyExistsException _){
        } catch (IOException io){
            SystemLogger.logWarning(FolderManager.class, io.getMessage());
        }
    }
}
