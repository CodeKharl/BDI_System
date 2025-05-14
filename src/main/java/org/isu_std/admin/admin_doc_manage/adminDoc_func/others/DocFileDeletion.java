package org.isu_std.admin.admin_doc_manage.adminDoc_func.others;

import org.isu_std.dao.DocumentDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.folder_setup.FolderConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class DocFileDeletion {
    private final DocumentDao documentDao;

    public DocFileDeletion(DocumentDao documentDao){
        this.documentDao = documentDao;
    }

    public void deletePerform(int barangayId, int documentId){
        try {
            String docFileName = getDocFileName(barangayId, documentId);
            String documentDirectory = FolderConfig.DOC_DOCUMENT_DIRECTORY.getDirectory();
            File file = new File(documentDirectory + File.separator + docFileName);

            Files.deleteIfExists(file.toPath());
        }catch (NotFoundException | IOException e){
            SystemLogger.logWarning(DocFileDeletion.class, e.getMessage());
            throw new OperationFailedException("Failed to delete the document file");
        }
    }

    private String getDocFileName(int barangayId, int documentId){
        Optional<String> optionalDocFileName = documentDao
                .getDocumentFileName(barangayId, documentId);

        return optionalDocFileName.orElseThrow(
                () -> new NotFoundException("Document file not found on the database!")
        );
    }
}
