package org.isu_std.io.file_setup;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.isu_std.io.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MSWord {
    private final static String FILE_FORMAT = ".docx";

    public static boolean isDocxFile(File file){
        return file.getName().endsWith(FILE_FORMAT);
    }

    public static void docxFileOrThrow(File file){
        if(!isDocxFile(file)){
            throw new IllegalArgumentException(MSWordMessage.NOT_DOCX_FILE_MESSAGE.getMessage());
        }
    }

    public static void editDocxPlaceHolders(File file){
        try(FileInputStream fis = new FileInputStream(file);
            XWPFDocument xwpfDocument = new XWPFDocument(fis)
        ){
            for(XWPFParagraph xwpfParagraph : xwpfDocument.getParagraphs()){
                String text = xwpfParagraph.getText();

            }
        }catch(IOException e){
            Util.printException(e.getMessage());
        }
    }
}
