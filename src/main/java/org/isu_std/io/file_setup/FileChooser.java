package org.isu_std.io.file_setup;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.isu_std.io.Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class FileChooser {
    private FileChooser(){}

    public static Optional<File> getOptionalDocFile(String fileType){
        var jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Select the %s file".formatted(fileType));

        JDialog jDialog = new JDialog();
        jDialog.setAlwaysOnTop(true);

        if(jFileChooser.showOpenDialog(jDialog) == JFileChooser.APPROVE_OPTION){
            return Optional.ofNullable(jFileChooser.getSelectedFile());
        }

        return Optional.empty();
    }

    public static void openFile(File file) throws IOException{
        Desktop.getDesktop().open(file);
    }
}
