package org.isu_std.admin.doc_output_file_provider;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.file_setup.DocxFileManager;
import org.isu_std.io.folder_setup.FolderConfig;
import org.isu_std.io.folder_setup.FolderManager;
import org.isu_std.models.UserPersonal;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class DocOutFileProvider {
    private final static String DOC_FILE_NAME_FORMAT = "%s_%s";

    public static boolean createOutputDocumentFile(RequestDocumentContext requestDocumentContext){
        FolderConfig outputFilePath = FolderConfig.DOC_APPROVE_PATH;
        FolderManager.setFileFolder(outputFilePath);

        try {
            File outputFile = copyDefaultFile(requestDocumentContext, outputFilePath);
            Map<String, String> informationMap = DocOutFileContext
                    .getValuesWithPlaceHolderMap(requestDocumentContext);

            DocxFileManager.editDocxPlaceHolders(outputFile, informationMap);

            return true;
        }catch (IOException e){
            SystemLogger.logWarning(DocOutFileProvider.class, e.getMessage());
        }

        return false;
    }

    private static File copyDefaultFile(
            RequestDocumentContext requestDocumentContext, FolderConfig folderConfig
    ) throws IOException{
        File defaultFile = requestDocumentContext.document().documentFile();
        String referenceId = requestDocumentContext.documentRequest().referenceId();
        String fileName = getDocFileName(referenceId, defaultFile.getName());

        // The doc output file
        File newFile = new File(folderConfig.getPath() + File.separator + fileName);

        DocxFileManager.copyFile(defaultFile, newFile);
        return newFile;
    }

    private static String getDocFileName(String preFix, String postFix){
        // handles the file name of the output file.
        return DOC_FILE_NAME_FORMAT.formatted(preFix, postFix);
    }
}
