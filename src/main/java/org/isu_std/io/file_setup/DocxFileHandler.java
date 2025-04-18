package org.isu_std.io.file_setup;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.isu_std.io.exception.OperationFailedException;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

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

    public static boolean containsPlaceHoldersInParagraphs(File file,  Set<String> placeHolders) throws IOException {
        // Checks if the document file has at least one placeHolder.
        try(InputStream inputStream = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(inputStream))
        {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if(isParagraphContainsPlaceHolders(paragraph, placeHolders)){
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isParagraphContainsPlaceHolders(XWPFParagraph paragraph, Set<String> placeHolders){
        for (XWPFRun run : paragraph.getRuns()) {
            String txt = run.getText(0);

            if (txt == null) {
                continue;
            }

            if (containsPlaceHolders(placeHolders, txt)) {
                return true;
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

    public static void editDocxPlaceHolders(File file, Map<String, String> informations) throws IOException, OperationFailedException{
        try(InputStream inputStream = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(inputStream);
            OutputStream outputStream = new FileOutputStream(file)
        ){
            for(XWPFParagraph paragraph : document.getParagraphs()){
                Optional<List<XWPFRun>> runList = Optional.ofNullable(paragraph.getRuns());
                runList.ifPresent((runs) -> modifiedProcess(runs, informations));
            }

            document.write(outputStream);
        }
    }

    private static void modifiedProcess(List<XWPFRun> runList, Map<String, String> informations)throws OperationFailedException{
        for(XWPFRun run : runList){
            String txt = run.getText(0);
            if(txt == null){
                continue;
            }

            String existingPlaceHolder = getExistingPlaceHolders(informations.keySet(), txt);
            if(existingPlaceHolder == null){
                continue;
            }

            String info = informations.get(existingPlaceHolder);
            txt = txt.replace(existingPlaceHolder, info);
            run.setText(txt, 0);
        }
    }

    private static String getParagraphTxt(List<XWPFRun> runList){
        var fullTxt = new StringBuilder();
        for(XWPFRun run : runList){
            String txt = run.getText(0);

            if(txt != null) {
                fullTxt.append(run.getText(0));
            }
        }

        return fullTxt.toString();
    }


    private static String getExistingPlaceHolders(Set<String> placeHolders, String txt){
        for(String holder : placeHolders){
            if(txt.contains(holder)){
                return holder;
            }
        }

        return null;
    }

    private static void clearRuns(XWPFParagraph paragraph){
        int numRuns = paragraph.getRuns().size();

        for(int i = numRuns - 1; i >= 0; i--){
            if(!paragraph.removeRun(i)){
                throw new OperationFailedException("Failed to clear the runs on the paragraph!");
            }
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
