package org.isu_std.admin.admin_main.approved_documents.approved_documents_export;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ApprovedDocExportController {
    private final ApprovedDocExportService approvedDocExportService;
    private final RequestDocumentContext requestDocumentContext;
    private final File outputDocumentFile;

    private Path targetPath;

    public ApprovedDocExportController(ApprovedDocExportService approvedDocExportService, RequestDocumentContext requestDocumentContext, File outputDocumentFile){
        this.approvedDocExportService = approvedDocExportService;
        this.requestDocumentContext = requestDocumentContext;
        this.outputDocumentFile = outputDocumentFile;
    }

    protected boolean isTargetPathSet(){
        Optional<Path> optionalPath = approvedDocExportService.getChosenDocFilePath();

        if(optionalPath.isPresent()){
            Path chosenPath = optionalPath.get();
            Util.printInformation("Target Path : " + chosenPath);

            this.targetPath = Paths.get(chosenPath.toString(), outputDocumentFile.getName());

            return true;
        }

        return false;
    }

    protected boolean exportPerformed() {
        try{
            approvedDocExportService.docFileMoveDirPerformed(outputDocumentFile, this.targetPath);
            approvedDocExportService.addReceipt(targetPath, requestDocumentContext);

            return true;
        }catch(OperationFailedException e) {
            Util.printException(e.getMessage());
        }

        return false;
    }
}
