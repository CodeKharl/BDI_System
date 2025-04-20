package org.isu_std.io.folder_setup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Folder {
    public static void setFileFolder(String filePath){
        if(new File(filePath).isDirectory()){
            return;
        }

        try{
            Files.createDirectories(Path.of(filePath));
        }catch (IOException _){}
    }
}
