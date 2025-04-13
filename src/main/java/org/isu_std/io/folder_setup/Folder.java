package org.isu_std.io.folder_setup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Folder {
    public static boolean createFolder(String filePath){
        try{
            Files.createDirectories(Path.of(filePath));
        }catch (IOException _){}

        return false;
    }
}
