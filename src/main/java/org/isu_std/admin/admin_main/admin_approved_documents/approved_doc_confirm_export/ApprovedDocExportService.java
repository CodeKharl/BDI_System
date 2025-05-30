package org.isu_std.admin.admin_main.admin_approved_documents.approved_doc_confirm_export;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.io.folder_setup.FolderChooser;
import org.isu_std.io.folder_setup.FolderManager;

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

    protected Optional<Path> getChosenDocFileDirectory(){
        return FolderChooser.getDirectory("Choose Document File Path");
    }

    private Path createNewFilePath(Path targetDirectory, File file){
        return Path.of(targetDirectory.toString(), file.getName());
    }

    protected void docFileMoveDirPerformed(File docFile, Path targetDirectory){
        try {
            Path docSourcePath = docFile.toPath();
            Path newFilePath = createNewFilePath(targetDirectory, docFile);

            FolderManager.setFileDirectory(targetDirectory);
            Files.move(docSourcePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException(
                    "Failed to export the document file! Reason : " + e.getMessage()
            );
        }
    }

    protected void addReceipt(Path targetDirectory, RequestDocumentContext requestDocumentContext) {
        try {
            ReceiptCreator.createReceiptFile(targetDirectory, requestDocumentContext);
        }catch (IOException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException(
                    "Failed to add receipt file to the target path! Reason : " + e.getMessage()
            );
        }
    }

    protected void deleteApprovedReqPerform(RequestDocumentContext requestDocumentContext) {
        String referenceId = requestDocumentContext.documentRequest().referenceId();

        try {
            if (!documentRequestDao.deleteDocRequest(referenceId)) {
                throw new OperationFailedException("Failed to delete the request! Please try again!");
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to delete request : " + referenceId);
        }
    }
}
