package org.isu_std.io.folder_setup;

import org.isu_std.io.SystemLogger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FolderManager {
    public static void setFileDirectory(FolderConfig folderConfig){
        try{
            Path path = Path.of(folderConfig.getDirectory());
            Files.createDirectories(path);
        }catch (FileAlreadyExistsException _){
        } catch (IOException io){
            SystemLogger.logWarning(FolderManager.class, io.getMessage());
        }
    }
}
