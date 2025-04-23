package org.isu_std.admin.admin_main.approved_documents.approved_documents_export;

import org.isu_std.admin.admin_main.ReqDocsManager;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ApprovedDocExportController {
    private final ApprovedDocExportService approvedDocExportService;
    private final File documentFile;

    private Path targetPath;

    public ApprovedDocExportController(ApprovedDocExportService approvedDocExportService, File documentFile){
        this.approvedDocExportService = approvedDocExportService;
        this.documentFile = documentFile;
    }

    protected boolean isTargetPathSet(){
        Optional<Path> optionalPath = approvedDocExportService.getChosenDocFilePath();

        if(optionalPath.isPresent()){
            Path chosenPath = optionalPath.get();
            Util.printInformation("Target Path : " + chosenPath);

            this.targetPath = Paths.get(chosenPath.toString(), documentFile.getName());

            return true;
        }

        return false;
    }

    protected boolean exportPerformed() {
        try{
            approvedDocExportService.docFileMoveDirPerformed(documentFile, targetPath);
            return true;
        }catch(IOException e) {
            Util.printException(e.getMessage());
        }

        return false;
    }
}
