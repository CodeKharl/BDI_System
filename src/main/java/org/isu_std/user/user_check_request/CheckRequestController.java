package org.isu_std.user.user_check_request;

import org.isu_std.client_context.UserContext;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.User;
import org.isu_std.user.user_check_request.user_check_status.CheckRequestStatus;
import org.isu_std.user.user_check_request.user_delete_request.UserDeleteRequest;
import org.isu_std.user.user_check_request.user_payment_manage.PaymentManage;

import java.util.List;
import java.util.Map;

public class CheckRequestController {
    private final CheckRequestService checkRequestService;
    private final RequestInfoContext requestInfoContext;
    private final RequestSelectContext requestSelectContext;

    public CheckRequestController(CheckRequestService checkRequestService, UserContext userContext){
        this.checkRequestService = checkRequestService;
        this.requestInfoContext = checkRequestService.createReqInfoManager(userContext.getUser());
        this.requestSelectContext = checkRequestService.createReqSelectManager();
    }

    protected boolean isExistingDocMapSet(){
       User user = requestInfoContext.getUser();

        try{
            List<DocumentRequest> userDocReqList = checkRequestService
                    .getUserDocReqMap(user.userId(), user.barangayId());
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
                requestInfoContext.getUser().barangayId(), userReqList
        );

        requestInfoContext.setDocumentDetailMap(documentDetailList);
    }

    protected void printDocumentDetails(){
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
        switch (choice){
            case 1 -> checkRequestStatus();
            case 2 -> paymentManage();
            case 3 -> {
                return deleteRequest();
            }
        }

        return false;
    }

    private void checkRequestStatus(){
        CheckRequestStatus checkRequestStatus = checkRequestService.createCheckRequestStatus(
                requestSelectContext.getSelectedDocRequest()
        );

        checkRequestStatus.checkRequestStatus();
    }
    private void paymentManage(){
        String referenceId = requestSelectContext.getSelectedDocRequest().referenceId();

        if(!checkRequestService.isRequestApproved(referenceId)){
            Util.printMessage("You cannot proceed in this section!");
            Util.printMessage("Please wait for the admin request approval.");
            return;
        }

        PaymentManage paymentManage = checkRequestService.createPaymentManage(requestSelectContext);
        paymentManage.sectionPerformed();
    }

    protected boolean deleteRequest(){
        UserDeleteRequest userDeleteRequest = checkRequestService
                .createUserDeleteRequest(requestSelectContext.getSelectedDocRequest());

        return userDeleteRequest.requestDeletePerform();
    }

    protected String selectedDocName(){
        return requestSelectContext.getSelectedDocument().documentName();
    }

    protected int docRequestListLength(){
        return requestInfoContext.getUserDocRequestList().size();
    }
}
