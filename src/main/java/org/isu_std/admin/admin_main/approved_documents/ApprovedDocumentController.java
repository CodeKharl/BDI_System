package org.isu_std.admin.admin_main.approved_documents;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.admin.admin_main.approved_documents.approved_doc_confirm_export.ApprovedDocExport;
import org.isu_std.admin.admin_main.req_files_view.RequirementFilesView;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Barangay;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.Payment;

import java.io.File;
import java.util.List;

public class ApprovedDocumentController {
    private final ApprovedDocumentService approvedDocumentService;
    private final Barangay barangay;

    private List<DocumentRequest> approvedDocList;
    private RequestDocumentContext requestDocumentContext;
    private File outputDocFile;

    protected ApprovedDocumentController(ApprovedDocumentService approvedDocumentService, Barangay barangay){
        this.approvedDocumentService = approvedDocumentService;
        this.barangay = barangay;
    }

    protected boolean isThereExistingApprovedRequests(){
        try {
            approvedDocList = approvedDocumentService
                    .getApproveDocList(barangay.barangayId());

            return true;
        }catch (ServiceException | NotFoundException e){
            Util.printMessage(e.getMessage());
        }

        return false;
    }

    protected void printApprovedDocuments(){
        Util.printSubSectionTitle("Approved Requests (Reference ID -> User ID - Document ID)");

        for(int i = 0; i < approvedDocList.size(); i++){
            String details = approvedDocList.get(i).getWithReferenceId();

            Util.printInformation(
                    "%d. %s".formatted(i + 1, details));
        }
    }

    protected int getApprovedDocsCount(){
        return this.approvedDocList.size();
    }

    protected boolean isDocumentRequestSet(int docsChoice){
        try{
            DocumentRequest documentRequest = approvedDocList.get(docsChoice - 1);
            this.requestDocumentContext = approvedDocumentService
                    .getRequestDocContext(documentRequest);

            return true;
        }catch (ServiceException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected String getApprovedSectionTitle(){
        DocumentRequest documentRequest = requestDocumentContext.documentRequest();

        return "Approved Document View -> (Reference ID -> %s : User ID - %d | Document ID - %d)"
                .formatted(
                        documentRequest.referenceId(),
                        documentRequest.userId(),
                        documentRequest.documentId()
                );
    }

    protected boolean isApprovedValidatingFinished(int choice){
        switch (choice){
            case 1 -> {
                return approvedDocExport();
            }

            case 2 -> viewApprovedFile();
            case 3 -> viewApprovedPayment();
            case 4 -> requestDocumentContext.document().printDetails();
            case 5 -> requestDocumentContext.userPersonal().printPersonalStats();
            case 6 -> requirementFilesView();
        }

        return false;
    }

    protected void requirementFilesView(){
        RequirementFilesView requirementFilesView = approvedDocumentService.getReqFilesView(
                requestDocumentContext.documentRequest().requirementDocList()
        );

        requirementFilesView.viewProcess();
    }

    private boolean setOutputDocFile(){
        try {
            if (this.outputDocFile == null) {
                this.outputDocFile = approvedDocumentService
                        .getApprovedDocFile(requestDocumentContext);
            }

            return true;
        }catch (NotFoundException e){
            Util.printMessage(e.getMessage());
        }

        return false;
    }

    private boolean approvedDocExport(){
        if(!isPaymentPresent() || !setOutputDocFile()){
            return false;
        }

        ApprovedDocExport approvedDocExport = approvedDocumentService
                .createApprovedDocExport(this.requestDocumentContext, this.outputDocFile);

        return approvedDocExport.isExported();
    }

    private void viewApprovedFile(){
        try {
            if (setOutputDocFile()) {
                Util.printMessage("Opening the user document file...");
                approvedDocumentService.openDocFile(this.outputDocFile);
            }
        }catch (ServiceException e){
            Util.printMessage(e.getMessage());
        }
    }

    private boolean isPaymentPresent(){
        try{
            approvedDocumentService.checkPayment(requestDocumentContext.payment());

            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        Util.printMessage("Payment information is not yet set.");
        return false;
    }

    private void viewApprovedPayment(){
        if(isPaymentPresent()){
            Payment payment = requestDocumentContext.payment();

            payment.printPaymentDetails();
        }
    }
}
