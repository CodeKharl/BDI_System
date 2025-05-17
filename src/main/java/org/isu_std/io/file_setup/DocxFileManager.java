package org.isu_std.io.file_setup;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.isu_std.io.custom_exception.OperationFailedException;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.*;

// Class that deals the docx files.

public class DocxFileManager {
    private final static String FILE_FORMAT = ".docx";
    private final static String PLACE_HOLDER_FORMAT = "{{%s}}";

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
                List<XWPFRun> runList = paragraph.getRuns();

                if(runList == null){
                    continue;
                }

                if(isParagraphContainsPlaceHolders(runList, placeHolders)){
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isParagraphContainsPlaceHolders(List<XWPFRun> runList, Set<String> placeHolders){
        String paragraphTxt = getParagraphTxt(runList);
        String placeHolder = getExistingPlaceHolders(placeHolders, paragraphTxt);

        return placeHolder != null;
    }

    private static String getExistingPlaceHolders(Set<String> placeHolders, String txt){
        for(String holder : placeHolders){
            if(txt.contains(holder)){
                return holder;
            }
        }

        return null;
    }

    private static String getParagraphTxt(List<XWPFRun> runList){
        var fullTxt = new StringBuilder();
        for(XWPFRun run : runList){
            String txt = run.getText(0);

            if(txt != null) {
                fullTxt.append(txt);
            }
        }

        return fullTxt.toString();
    }


    public static void editDocxPlaceHolders(File file, Map<String, String> informations) throws IOException{
        try(InputStream inputStream = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(inputStream);
            OutputStream outputStream = new FileOutputStream(file)
        ){
            for(XWPFParagraph paragraph : document.getParagraphs()){
                Optional<List<XWPFRun>> runList = Optional.ofNullable(paragraph.getRuns());
                runList.ifPresent((runs) -> editOnProcess(runs, informations));
            }

            document.write(outputStream);
        }
    }

    private static void editOnProcess(List<XWPFRun> runList, Map<String, String> informations){
        List<Integer> runStartIndexes = new ArrayList<>();
        String paragraphTxt = getParagraphTxt(runList, runStartIndexes);

        for(Map.Entry<String, String> entry : informations.entrySet()){
            String placeHolder = entry.getKey();
            String replacement = entry.getValue();

            int index;
            while((index = paragraphTxt.indexOf(placeHolder)) != - 1){
                int endIndex = index + placeHolder.length();

                List<Integer> affectedRuns = getAffectedRuns(runList, runStartIndexes, index, endIndex);
                clearRunsText(runList, affectedRuns);
                setReplacementWithStyle(runList, affectedRuns, replacement);

                paragraphTxt = paragraphTxt.substring(0, index)
                        + replacement + paragraphTxt.substring(endIndex);

                runStartIndexes = reCalculateRunStartIndex(runList);
            }
        }
    }

    private static String getParagraphTxt(List<XWPFRun> runList, List<Integer> runStartIndexes){
        var fullTxt = new StringBuilder();
        for(XWPFRun run : runList){
            runStartIndexes.add(fullTxt.length());
            String txt = run.getText(0);

            if(txt != null) {
                fullTxt.append(txt);
            }
        }

        return fullTxt.toString();
    }

    private static List<Integer> getAffectedRuns(List<XWPFRun> runList, List<Integer> runStartIndexes, int startIndex, int endIndex){
        List<Integer> indices = new ArrayList<>();

        for(int i = 0; i < runList.size(); i++){
            XWPFRun run = runList.get(i);
            String txt = run.getText(0);

            if(txt == null){
                continue;
            }

            int runStart = runStartIndexes.get(i);
            int runEnd = runStart + txt.length();

            if(runEnd > startIndex && runStart < endIndex){
                indices.add(i);
            }
        }

        return indices;
    }

    private static void setReplacementWithStyle(List<XWPFRun> runList, List<Integer> affectedIndices, String replacement){
        if(affectedIndices.isEmpty()){
            return;
        }

        XWPFRun baseRun = runList.get(affectedIndices.getFirst());
        baseRun.setText(replacement, 0);
    }

    private static void clearRunsText(List<XWPFRun> runList, List<Integer> indices){
        for(int idx : indices){
            runList.get(idx).setText("", 0);
        }
    }

    private static List<Integer> reCalculateRunStartIndex(List<XWPFRun> runList){
        List<Integer> indexes = new ArrayList<>();

        int currentLength = 0;
        for(XWPFRun run : runList){
            indexes.add(currentLength);

            String txt = run.getText(0);
            if(txt != null){
                currentLength += txt.length();
            }
        }

        return indexes;
    }

    public static Set<String> convertRecComToPlaceHoldersSet(RecordComponent[] recordComponents){
        Set<String> fieldSet = new HashSet<>();

        for(RecordComponent recordComponent : recordComponents){
            fieldSet.add(PLACE_HOLDER_FORMAT.formatted(recordComponent.getName()));
        }

        return fieldSet;
    }

    public static <T> String convertToPlaceholder(T type){
        return PLACE_HOLDER_FORMAT.formatted(type.toString());
    }

    public static void copyFile(File sourceFile, File newFile) throws IOException{
        try(InputStream inputStream = new FileInputStream(sourceFile);
            OutputStream outputStream = new FileOutputStream(newFile)
        ){
            byte[] bytes = new byte[8192];
            int reader;

            while ((reader = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, reader);
            }
        }
    }
}
