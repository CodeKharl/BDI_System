package org.isu_std.admin.admin_main.approved_documents.approved_documents_export;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.folder_setup.FolderChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ApprovedDocExportService {
    protected Optional<Path> getChosenDocFilePath(){
        return FolderChooser.getPath("Choose Document File Path");
    }

    protected void docFileMoveDirPerformed(File docFile, Path targetPath){
        try {
            Path docSourcePath = docFile.toPath();
            Files.move(docSourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new OperationFailedException(
                    "Failed to export the document file! Reason : " + e.getMessage()
            );
        }
    }

    protected void addReceipt(Path targetPath, RequestDocumentContext requestDocumentContext){
        try {
            ReceiptCreator.createReceiptFile("Receipt!", targetPath, requestDocumentContext);
        }catch (IOException e){
            throw new OperationFailedException(
                    "Failed to add receipt file to the target path! Reason : " + e.getMessage()
            );
        }
    }
}
