package org.isu_std.user.user_check_request;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.User;
import org.isu_std.user.user_check_request.user_check_status.CheckReqStatusController;
import org.isu_std.user.user_check_request.user_check_status.CheckReqStatusService;
import org.isu_std.user.user_check_request.user_check_status.CheckRequestStatus;
import org.isu_std.user.user_check_request.user_delete_request.UserDeleteReqController;
import org.isu_std.user.user_check_request.user_delete_request.UserDeleteReqService;
import org.isu_std.user.user_check_request.user_delete_request.UserDeleteRequest;
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

    protected RequestInfoContext createReqInfoManager(User user){
        return new RequestInfoContext(user);
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
            throw new NotFoundException(
                    "Theres no existing document detail for the existing request."
            );
        }

        return documentDetailList;
    }

    protected CheckRequestStatus createCheckRequestStatus(DocumentRequest documentRequest){
        var checkReqStatusService = new CheckReqStatusService(documentRequestDao);
        var checkReqStatusController = new CheckReqStatusController(
                checkReqStatusService, documentRequest
        );

        return new CheckRequestStatus(checkReqStatusController);
    }

    protected PaymentManage createPaymentManage(RequestSelectContext requestSelectContext){
        PaymentManageService paymentManageService = new PaymentManageService(paymentDao, documentDao);
        PaymentManageController paymentManageController = new PaymentManageController(
                paymentManageService, requestSelectContext
        );

        return new PaymentManage(paymentManageController);
    }

    protected UserDeleteRequest createUserDeleteRequest(DocumentRequest documentRequest){
        var userDeleteReqService = new UserDeleteReqService(documentRequestDao, paymentDao);
        var userDeleteReqController = new UserDeleteReqController(
                userDeleteReqService, documentRequest
        );


        return new UserDeleteRequest(userDeleteReqController);
    }

    protected boolean isRequestApproved(String referenceId){
        return documentRequestDao.isRequestApproved(referenceId);
    }
}
