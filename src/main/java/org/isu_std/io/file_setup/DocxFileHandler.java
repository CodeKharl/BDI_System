package org.isu_std.io.file_setup;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.isu_std.io.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

// Class that deals the docx files.

public class DocxFileHandler {
    private final static String FILE_FORMAT = ".docx";

    public static boolean isDocxFile(File file){
        return file.getName().endsWith(FILE_FORMAT);
    }

    public static void docxFileOrThrow(File file){
        if(!isDocxFile(file)){
            throw new IllegalArgumentException(
                    DocxMessage.NOT_DOCX_FILE_MESSAGE.getMessage()
            );
        }
    }

    public static boolean containsPlaceHoldersInParagraphs(XWPFDocument xwpfDocument, Set<String> placeHolders) {
        // Checks if the document file has at least one placeHolder.
        for (XWPFParagraph paragraph : xwpfDocument.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String txt = run.getText(0);

                if (txt == null) {
                    continue;
                }

                if (containsPlaceHolders(placeHolders, txt)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean containsPlaceHolders(Set<String> placeHolders, String txt){
        for(String holder : placeHolders){
            if(txt.contains(holder)) {
                return true;
            }
        }

        return false;
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

    public static Set<String> convertFieldsToPlaceHoldersSet(Field[] fields){
        Set<String> fieldSet = new HashSet<>();

        for(Field field : fields){
            fieldSet.add("{{%s}}".formatted(field.getName()));
        }

        return fieldSet;
    }
}
