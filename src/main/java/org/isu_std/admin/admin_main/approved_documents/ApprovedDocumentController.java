package org.isu_std.admin.admin_main.approved_documents;

import org.isu_std.admin.admin_main.ReqDocsManager;
import org.isu_std.admin.admin_main.req_files_view.RequirementFilesView;
import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Barangay;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.Payment;

import java.io.File;
import java.util.List;

public class ApprovedDocumentController {
    private final ApprovedDocumentService approvedDocumentService;
    private final Barangay barangay;
    private List<DocumentRequest> approvedDocList;
    private ReqDocsManager reqDocsManager;

    protected ApprovedDocumentController(ApprovedDocumentService approvedDocumentService, Barangay barangay){
        this.approvedDocumentService = approvedDocumentService;
        this.barangay = barangay;
    }

    protected boolean isThereExistingApprovedRequests(){
        try {
            approvedDocList = approvedDocumentService.getApproveDocList(barangay.barangayId());
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
            this.reqDocsManager = approvedDocumentService.getReqDocsManager(documentRequest);

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
            case 3 -> viewApprovedPayment();
            case 4 -> reqDocsManager.document().printDetails();
            case 5 -> reqDocsManager.userPersonal().printPersonalStats();
            case 6 -> requirementFilesView();
        }
    }

    protected void requirementFilesView(){
        RequirementFilesView requirementFilesView = approvedDocumentService.getReqFilesView(
                reqDocsManager.documentRequest().requirementDocList()
        );

        requirementFilesView.viewProcess();
    }

    protected void viewApprovedFile(){
        try{
            File docFile = approvedDocumentService.getApprovedDocFile(reqDocsManager);
            Util.printMessage("Opening the user document file...");
            approvedDocumentService.openDocFile(docFile);
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }
    }

    protected void viewApprovedPayment(){
        try{
            Payment payment = approvedDocumentService.getPayment(reqDocsManager.payment());
            Util.printInformation(payment.toString());
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }
    }
}
