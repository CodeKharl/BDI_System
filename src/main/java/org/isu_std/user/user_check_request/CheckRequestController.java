package org.isu_std.user.user_check_request;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;

import java.util.List;
import java.util.Map;

public class CheckRequestController {
    private final CheckRequestService checkRequestService;
    private final ReqInfoManager reqInfoManager;
    private final ReqSelectManager reqSelectManager;

    public CheckRequestController(CheckRequestService checkRequestService, int barangayId, int userId){
        this.checkRequestService = checkRequestService;
        this.reqInfoManager = checkRequestService.createReqInfoManager(barangayId, userId);
        this.reqSelectManager = checkRequestService.createReqSelectManager();
    }

    protected boolean isExistingDocMapSet(){
        try{
            List<DocumentRequest> userDocReqList = checkRequestService
                    .getUserDocReqMap(reqInfoManager.getUserId(), reqInfoManager.getBarangayId());
            this.reqInfoManager.setRefWithDocIDMap(userDocReqList);

            setDocumentDetailMap(userDocReqList);
            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void setDocumentDetailMap(List<DocumentRequest> userReqList) throws NotFoundException{
        Map<Integer, Document> documentDetailList = checkRequestService.getDocumentDetailMap(
                reqInfoManager.getBarangayId(), userReqList
        );

        reqInfoManager.setDocumentDetailMap(documentDetailList);
    }

    protected void printDocumentDetails(){
        Util.printSubSectionTitle("Existing Request (Document Name - Price - Requirements");
        Util.printMessage("Note! Requested document cannot be found once the admin reject it.");

        List<DocumentRequest> documentRequestList = reqInfoManager.getUserDocRequestList();
        Map<Integer, Document> documentDetailsMap = reqInfoManager.getDocumentDetailMap();

        for(int i = 0; i < documentDetailsMap.size(); i++){
            int documentId = documentRequestList.get(i).documentId();
            Document document = documentDetailsMap.get(documentId);

            Util.printInformation("%d. %s".formatted(i + 1, document.getDetails()));
        }
    }

    protected void setChosenDocument(int documentChoice){
        int index = documentChoice - 1;
        DocumentRequest selectedDocRequest = reqInfoManager.getUserDocRequestList().get(index);
        reqSelectManager.setSelectedDocRequest(selectedDocRequest);

        int documentId = selectedDocRequest.documentId();
        setSelectedDocument(documentId);
    }

    private void setSelectedDocument(int documentId){
        Document selectedDocument = reqInfoManager.getDocumentDetailMap().get(documentId);
        reqSelectManager.setSelectedDocument(selectedDocument);
    }

    protected boolean isRequestProcessFinished(int choice){
        switch (choice){
            case 1 -> checkRequestStatus();
            case 2 -> {}
            case 3 -> {
                return requestCancellationPerformed();
            }
        }

        return false;
    }

    protected void checkRequestStatus(){
        String referenceId = reqSelectManager.getSelectedDocRequest().referenceId();

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
                    reqSelectManager.getSelectedDocRequest()
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

    protected String selectedDocName(){
        return reqSelectManager.getSelectedDocument().documentName();
    }

    protected int docRequestListLength(){
        return reqInfoManager.getUserDocRequestList().size();
    }
}
