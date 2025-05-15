package org.isu_std.admin.admin_main.approved_documents.approved_doc_confirm_export;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class ApprovedDocExportController {
    private final ApprovedDocExportService approvedDocExportService;
    private final RequestDocumentContext requestDocumentContext;
    private final File outputDocumentFile;

    private Path targetDirectory;

    public ApprovedDocExportController(ApprovedDocExportService approvedDocExportService, RequestDocumentContext requestDocumentContext, File outputDocumentFile){
        this.approvedDocExportService = approvedDocExportService;
        this.requestDocumentContext = requestDocumentContext;
        this.outputDocumentFile = outputDocumentFile;
    }

    protected boolean isTargetPathSet(){
        Optional<Path> optionalPath = approvedDocExportService.getChosenDocFileDirectory();

        if(optionalPath.isPresent()){
            Path chosenPath = optionalPath.get();
            Util.printInformation("Target Path : " + chosenPath);

            this.targetDirectory = Path.of(
                    chosenPath.toString(), requestDocumentContext.getReferenceId()
            );
            return true;
        }

        return false;
    }

    protected boolean exportPerform() {
        try{
            approvedDocExportService.docFileMoveDirPerformed(
                    outputDocumentFile, this.targetDirectory
            );
            approvedDocExportService.addReceipt(targetDirectory, requestDocumentContext);

            approvedDocExportService.deleteApprovedReqPerform(requestDocumentContext);
            return true;
        }catch(OperationFailedException e) {
            Util.printException(e.getMessage());
        }

        return false;
    }
}
