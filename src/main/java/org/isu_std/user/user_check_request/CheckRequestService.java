package org.isu_std.user.user_check_request;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.user.user_check_request.user_payment_manage.PaymentManage;
import org.isu_std.user.user_check_request.user_payment_manage.PaymentManageController;
import org.isu_std.user.user_check_request.user_payment_manage.PaymentManageService;

import java.util.*;

public class CheckRequestService {
    private final DocumentRequestDao documentRequestDao;
    private final DocumentDao documentDao;
    private final PaymentDao paymentDao;

    public CheckRequestService(DocumentRequestDao documentRequestDao, DocumentDao documentDao, PaymentDao paymentDao){
        this.documentRequestDao = documentRequestDao;
        this.documentDao = documentDao;
        this.paymentDao = paymentDao;
    }

    protected RequestInfoContext createReqInfoManager(int barangayId, int userId){
        return new RequestInfoContext(barangayId, userId);
    }

    protected RequestSelectContext createReqSelectManager(){
        return new RequestSelectContext();
    }

    protected List<DocumentRequest> getUserDocReqMap(int userId, int barangayId){
        List<DocumentRequest> userDocReqList = documentRequestDao.getUserReqDocList(userId, barangayId);

        if(userDocReqList.isEmpty()){
            throw new NotFoundException("Theres no existing request found!");
        }

        return userDocReqList;
    }

    protected Map<Integer, Document> getDocumentDetailMap(int barangayId, List<DocumentRequest> userDocReqList){
        Map<Integer, Document> documentDetailList = new HashMap<>();

        userDocReqList.forEach((docReq) -> {
            int documentId = docReq.documentId();
            Optional<Document> optionalDocDetail = documentDao.getOptionalDocDetail(barangayId, documentId);

            optionalDocDetail.ifPresent(
                    (docDetail) -> documentDetailList.put(documentId, docDetail)
            );
        });

        if(documentDetailList.isEmpty()){
            throw new NotFoundException("Theres no existing document detail for the existing request.");
        }

        return documentDetailList;
    }


    protected boolean checkRequestedStatus(String referenceId){
        return documentRequestDao.isRequestApproved(referenceId);
    }

    protected void deleteRequestPerformed(DocumentRequest documentRequest){
        if(!documentRequestDao.deleteDocRequest(documentRequest)){
            throw new OperationFailedException("Failed to delete the request! Please try to cancel it again.");
        }
    }

    protected PaymentManage createPaymentManage(RequestSelectContext requestSelectContext){
        PaymentManageService paymentManageService = new PaymentManageService(paymentDao, documentDao);
        PaymentManageController paymentManageController = new PaymentManageController(
                paymentManageService, requestSelectContext
        );

        return new PaymentManage(paymentManageController);
    }
}
