package org.isu_std.admin.admin_main.approved_documents.approved_documents_export;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;

public class ApprovedDocExport {
    private final ApprovedDocExportController approvedDocExportController;

    public ApprovedDocExport(ApprovedDocExportController approvedDocExportController){
        this.approvedDocExportController = approvedDocExportController;
    }

    public boolean isExported(){
        if(!approvedDocExportController.isTargetPathSet()){
            return false;
        }

        if(!isConfirmedToExport()){
            return false;
        }

        if(approvedDocExportController.exportPerformed()){
            Util.printMessage("File has been export to the target path : ");
            return true;
        }

        return false;
    }


    protected boolean isConfirmedToExport(){
        return SystemInput.isPerformConfirmed(
                "File Export Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
