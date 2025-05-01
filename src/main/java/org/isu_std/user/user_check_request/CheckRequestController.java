package org.isu_std.user.user_check_request;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.user.user_check_request.user_payment_manage.PaymentManage;

import java.util.List;
import java.util.Map;

public class CheckRequestController {
    private final CheckRequestService checkRequestService;
    private final RequestInfoContext requestInfoContext;
    private final RequestSelectContext requestSelectContext;

    public CheckRequestController(CheckRequestService checkRequestService, int barangayId, int userId){
        this.checkRequestService = checkRequestService;
        this.requestInfoContext = checkRequestService.createReqInfoManager(barangayId, userId);
        this.requestSelectContext = checkRequestService.createReqSelectManager();
    }

    protected boolean isExistingDocMapSet(){
        try{
            List<DocumentRequest> userDocReqList = checkRequestService
                    .getUserDocReqMap(requestInfoContext.getUserId(), requestInfoContext.getBarangayId());
            this.requestInfoContext.setRefWithDocIDMap(userDocReqList);

            setDocumentDetailMap(userDocReqList);
            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void setDocumentDetailMap(List<DocumentRequest> userReqList) throws NotFoundException{
        Map<Integer, Document> documentDetailList = checkRequestService.getDocumentDetailMap(
                requestInfoContext.getBarangayId(), userReqList
        );

        requestInfoContext.setDocumentDetailMap(documentDetailList);
    }

    protected void printDocumentDetails(){
        Util.printSubSectionTitle("Existing Request (Document Name - Price - Requirements");
        Util.printMessage("Note! Requested document cannot be found once the admin reject it.");

        List<DocumentRequest> documentRequestList = requestInfoContext.getUserDocRequestList();
        Map<Integer, Document> documentDetailsMap = requestInfoContext.getDocumentDetailMap();

        for(int i = 0; i < documentDetailsMap.size(); i++){
            int documentId = documentRequestList.get(i).documentId();
            Document document = documentDetailsMap.get(documentId);

            Util.printInformation("%d. %s".formatted(i + 1, document.getDetails()));
        }
    }

    protected void setChosenDocument(int documentChoice){
        int index = documentChoice - 1;
        DocumentRequest selectedDocRequest = requestInfoContext.getUserDocRequestList().get(index);
        requestSelectContext.setSelectedDocRequest(selectedDocRequest);

        int documentId = selectedDocRequest.documentId();
        setSelectedDocument(documentId);
    }

    private void setSelectedDocument(int documentId){
        Document selectedDocument = requestInfoContext.getDocumentDetailMap().get(documentId);
        requestSelectContext.setSelectedDocument(selectedDocument);
    }

    protected boolean isRequestProcessFinished(int choice){
        String referenceId = requestSelectContext.getSelectedDocRequest().referenceId();

        switch (choice){
            case 1 -> checkRequestStatus(referenceId);
            case 2 -> paymentManage(referenceId);
            case 3 -> {
                return requestCancellationPerformed();
            }
        }

        return false;
    }

    protected void checkRequestStatus(String referenceId){
        if(!checkRequestService.checkRequestedStatus(referenceId)){
            Util.printMessage("Your request is in validation state! Please wait for admin approval.");
            return;
        }

        Util.printMessage("Your request has been approved!");
        Util.printMessage("You can now proceed to payment selection (Payment Manage)");
    }

    private boolean requestCancellationPerformed(){
        if(!isRequestCancellationConfirmed()){
            return false;
        }

        try{
            checkRequestService.deleteRequestPerformed(
                    requestSelectContext.getSelectedDocRequest()
            );

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    private boolean isRequestCancellationConfirmed(){
        return SystemInput.isPerformConfirmed(
                "Cancellation Request Confirm",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.EXIT_CODE.getValue()
        );
    }

    private void paymentManage(String referenceId){
        if(!checkRequestService.checkRequestedStatus(referenceId)){
            Util.printMessage("You cannot proceed in this section!");
            Util.printMessage("Please wait for the admin request approval");
            return;
        }

        PaymentManage paymentManage = checkRequestService.createPaymentManage(requestSelectContext);
        paymentManage.sectionPerformed();
    }

    protected String selectedDocName(){
        return requestSelectContext.getSelectedDocument().documentName();
    }

    protected int docRequestListLength(){
        return requestInfoContext.getUserDocRequestList().size();
    }
}
