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

    private DocumentRequest selectedDocRequest;
    private Document selectedDocument;

    public CheckRequestController(CheckRequestService checkRequestService, int barangayId, int userId){
        this.checkRequestService = checkRequestService;
        this.reqInfoManager = checkRequestService.createReqInfoManager(barangayId, userId);
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
        Util.printSubSectionTitle("Existing Request (Document ID -> Document Name - Price - Requirements");

        Map<Integer, Document> documentDetailsMap = reqInfoManager.getDocumentDetailMap();
        documentDetailsMap.forEach((documentId, document) ->
            Util.printInformation(
                    "%d -> %s".formatted(documentId, document.getDetails())
            )
        );
    }

    protected boolean isChosenDocumentSet(int inputDocId){
        try{
            this.selectedDocument = checkRequestService
                    .getCheckDocument(reqInfoManager.getDocumentDetailMap(), inputDocId);

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean isRequestProcessFinished(int choice){
        switch (choice){
            case 1 -> {}
            case 2 -> {}
            case 3 -> {
                return requestCancellationPerformed();
            }
        }

        return false;
    }

    private boolean requestCancellationPerformed(){
        if(!isRequestCancellationConfirmed()){
            return false;
        }

        try{
            checkRequestService.deleteRequestPerformed();

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
        return selectedDocument.documentName();
    }
}
