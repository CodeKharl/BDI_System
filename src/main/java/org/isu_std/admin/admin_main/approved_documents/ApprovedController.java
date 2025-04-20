package org.isu_std.admin.admin_main.approved_documents;

import org.isu_std.admin.admin_main.ReqDocsManager;
import org.isu_std.admin.admin_main.req_files_view.RequirementFilesView;
import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Barangay;
import org.isu_std.models.DocumentRequest;

import java.util.List;

public class ApprovedController {
    private final ApprovedService approvedService;
    private final Barangay barangay;
    private List<DocumentRequest> approvedDocList;
    private ReqDocsManager reqDocsManager;

    protected ApprovedController(ApprovedService approvedService, Barangay barangay){
        this.approvedService = approvedService;
        this.barangay = barangay;
    }

    protected boolean isThereExistingApprovedRequests(){
        try {
            approvedDocList = approvedService.getApproveDocList(barangay.barangayId());
            return true;
        }catch (NotFoundException e){
            Util.printMessage(e.getMessage());
        }

        return false;
    }

    protected void printApprovedDocuments(){
        Util.printSectionTitle("Approved Requests (User ID - Document ID)");
        Util.printListWithCount(approvedDocList);
    }

    protected int getApprovedDocsCount(){
        return this.approvedDocList.size();
    }

    protected boolean isDocumentRequestSet(int docsChoice){
        try{
            DocumentRequest documentRequest = approvedDocList.get(docsChoice - 1);
            this.reqDocsManager = approvedService.getReqDocsManager(documentRequest);

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected String getApprovedSectionTitle(){
        return "Approved Document View -> (User ID - %d | Document ID - %d)"
                .formatted(reqDocsManager.getUserId(), reqDocsManager.getDocumentId());
    }

    protected void approvedValidatingOnProcess(int choice){
        switch (choice){
            case 1 -> {}
            case 2 -> viewApprovedFile();
            case 3 -> reqDocsManager.getDocument().printDetails();
            case 4 -> reqDocsManager.getUserPersonal().printPersonalStats();
            case 5 -> requirementFilesView();
        }
    }

    protected void requirementFilesView(){
        RequirementFilesView requirementFilesView = approvedService.getReqFilesView(
                reqDocsManager.getDocumentRequest().requirementDocList()
        );

        requirementFilesView.viewProcess();
    }

    protected void viewApprovedFile(){
        try{
            Util.printMessage("Opening the user document file...");
            approvedService.openApprovedDocFile(reqDocsManager);
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }
    }
}
