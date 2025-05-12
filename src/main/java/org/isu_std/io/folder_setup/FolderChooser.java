package org.isu_std.io.folder_setup;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class FolderChooser {
    public static Optional<Path> getDirectory(String dialogTitle){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle(dialogTitle);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int chooserValue = jFileChooser.showOpenDialog(null);

        if(chooserValue == JFileChooser.CANCEL_OPTION){
            return Optional.empty();
        }

        File file = jFileChooser.getCurrentDirectory();
        return Optional.of(file.toPath());
    }
}
