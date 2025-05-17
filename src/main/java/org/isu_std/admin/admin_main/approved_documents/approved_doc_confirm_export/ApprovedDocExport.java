package org.isu_std.admin.admin_main.approved_documents.approved_doc_confirm_export;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections_enum.ChoiceCollection;

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

        if(approvedDocExportController.exportPerform()){
            Util.printMessage("The approved file has been exported.");
            Util.printMessage("Your request will now be deleted.");
            Util.printMessage("The transaction will be closed.");
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
