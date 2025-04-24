package org.isu_std.admin.admin_main.approved_documents.approved_documents_export;

import org.isu_std.admin.admin_main.ReqDocsManager;
import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ApprovedDocExportController {
    private final ApprovedDocExportService approvedDocExportService;
    private final ReqDocsManager reqDocsManager;
    private final File outputDocumentFile;

    private Path targetPath;

    public ApprovedDocExportController(ApprovedDocExportService approvedDocExportService, ReqDocsManager reqDocsManager, File outputDocumentFile){
        this.approvedDocExportService = approvedDocExportService;
        this.reqDocsManager = reqDocsManager;
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
            approvedDocExportService.addReceipt(targetPath, reqDocsManager);

            return true;
        }catch(OperationFailedException e) {
            Util.printException(e.getMessage());
        }

        return false;
    }
}
