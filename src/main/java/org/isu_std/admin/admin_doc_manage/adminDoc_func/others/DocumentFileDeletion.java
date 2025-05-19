package org.isu_std.admin.admin_doc_manage.adminDoc_func.others;

import org.isu_std.dao.DocumentDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.folder_setup.FolderConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class DocumentFileDeletion {
    private final DocumentDao documentDao;

    public DocumentFileDeletion(DocumentDao documentDao){
        this.documentDao = documentDao;
    }

    public void deletePerform(int barangayId, int documentId) throws OperationFailedException {
        try {
            String docFileName = getDocFileName(barangayId, documentId);
            String documentDirectory = FolderConfig.DOC_DOCUMENT_DIRECTORY.getDirectory();
            File file = new File(documentDirectory + File.separator + docFileName);

            Files.deleteIfExists(file.toPath());
        }catch (NotFoundException | IOException | DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new OperationFailedException(
                    "Failed to delete the document file with document ID : " + documentId, e
            );
        }
    }

    private String getDocFileName(int barangayId, int documentId){
        Optional<String> optionalDocFileName = documentDao
                .findDocumentFileName(barangayId, documentId);

        return optionalDocFileName.orElseThrow(
                () -> new NotFoundException("Document file not found on the database!")
        );
    }
}
