package org.isu_std.doc_output_file_provider;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.file_setup.DocxFileManager;
import org.isu_std.io.file_setup.FileManager;
import org.isu_std.io.folder_setup.FolderConfig;
import org.isu_std.io.folder_setup.FolderManager;
import org.isu_std.user.user_check_request.RequestSelectContext;

import java.io.*;
import java.util.Map;

public final class DocOutFileManager {
    private final static String DOC_FILE_NAME_FORMAT = "%s_%s";
    private final static FolderConfig outputFileDir = FolderConfig.DOC_APPROVE_DIRECTORY;

    public static boolean createOutputDocumentFile(RequestDocumentContext requestDocumentContext){
        FolderManager.setFileDirectory(outputFileDir);

        try {
            File outputFile = copyDefaultFile(requestDocumentContext);
            Map<String, String> informationMap = DocOutFileContext
                    .getValuesWithPlaceHolderMap(requestDocumentContext);

            DocxFileManager.editDocxPlaceHolders(outputFile, informationMap);

            return true;
        }catch (IOException e){
            SystemLogger.logWarning(DocOutFileManager.class, e.getMessage());
        }

        return false;
    }

    private static File copyDefaultFile(RequestDocumentContext requestDocumentContext) throws IOException{
        // The doc output file
        String outputDocFilePathName = getOutputDocFilePathName(requestDocumentContext);
        File newFile = new File(outputDocFilePathName);

        File defaultFile = requestDocumentContext.document().documentFile();
        DocxFileManager.copyFile(defaultFile, newFile);
        return newFile;
    }

    public static void deleteOutputDocFile(RequestDocumentContext requestDocumentContext){
        String outputFilePathName = getOutputDocFilePathName(requestDocumentContext);
        deleteFile(outputFilePathName);
    }

    public static void deleteOutputDocFile(RequestSelectContext requestSelectContext){
        String outputDocPathName = getOutputDocFilePathName(requestSelectContext);
        deleteFile(outputDocPathName);
    }

    private static void deleteFile(String filePathName){
        File file = new File(filePathName);

        if(file.exists()){
            FileManager.deleteFile(file.toPath());
        }
    }

    public static String getOutputDocFilePathName(RequestDocumentContext requestDocumentContext){
        String referenceId = requestDocumentContext.documentRequest().referenceId();
        File defaultFile = requestDocumentContext.document().documentFile();
        return outputFileNamePath(referenceId, defaultFile);
    }

    public static String getOutputDocFilePathName(RequestSelectContext requestSelectContext){

        String referenceId = requestSelectContext.getSelectedDocRequest().referenceId();
        File defaultFile = requestSelectContext.getSelectedDocument().documentFile();
        return outputFileNamePath(referenceId, defaultFile);
    }

    private static String outputFileNamePath(String referenceId, File defaultFile){
        String fileName = getDocFileName(referenceId, defaultFile.getName());
        return outputFileDir.getDirectory() + File.separator + fileName;
    }

    private static String getDocFileName(String preFix, String postFix){
        // handles the file name of the output file.
        return DOC_FILE_NAME_FORMAT.formatted(preFix, postFix);
    }
}
