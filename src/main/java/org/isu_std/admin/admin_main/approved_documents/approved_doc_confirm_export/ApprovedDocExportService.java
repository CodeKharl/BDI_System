package org.isu_std.admin.admin_main.approved_documents.approved_doc_confirm_export;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.doc_output_file_provider.DocOutFileManager;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.folder_setup.FolderChooser;
import org.isu_std.models.DocumentRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ApprovedDocExportService {
    private final DocumentRequestDao documentRequestDao;

    public ApprovedDocExportService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    Optional<Path> getChosenDocFilePath(){
        return FolderChooser.getDirectory("Choose Document File Path");
    }

    void docFileMoveDirPerformed(File docFile, Path targetPath){
        try {
            Path docSourcePath = docFile.toPath();
            Files.move(docSourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new OperationFailedException(
                    "Failed to export the document file! Reason : " + e.getMessage()
            );
        }
    }

    void addReceipt(Path targetPath, RequestDocumentContext requestDocumentContext){
        try {
            ReceiptCreator.createReceiptFile("Receipt!", targetPath, requestDocumentContext);
        }catch (IOException e){
            throw new OperationFailedException(
                    "Failed to add receipt file to the target path! Reason : " + e.getMessage()
            );
        }
    }

    void deleteRequestPerform(RequestDocumentContext requestDocumentContext){
        DocumentRequest documentRequest = requestDocumentContext.documentRequest();

        if(!documentRequestDao.deleteDocRequest(documentRequest)){
            DocOutFileManager.deleteOutputDocFile(requestDocumentContext);
        }

        throw new OperationFailedException("Failed to delete the request! Please try again!");
    }
}
