package org.isu_std.user.user_check_request;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
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

    protected RequestInfoContext createReqInfoContext(User user){
        return new RequestInfoContext(user);
    }

    protected RequestSelectContext createReqSelectContext(){
        return new RequestSelectContext();
    }

    protected List<DocumentRequest> getUserDocReqMap(int userId, int barangayId){
        try {
            List<DocumentRequest> userDocReqList = documentRequestDao.getUserReqDocList(userId, barangayId);

            if (userDocReqList.isEmpty()) {
                throw new NotFoundException("Theres no existing request found!");
            }

            return userDocReqList;
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch list of user requested document with user_id : " + userId);
        }
    }

    protected Map<Integer, Document> getDocumentDetailMap(int barangayId, List<DocumentRequest> userDocReqList){
        Map<Integer, Document> documentDetailMap = new HashMap<>();

        setDocDetailMap(documentDetailMap, userDocReqList, barangayId);

        if(documentDetailMap.isEmpty()){
            throw new NotFoundException(
                    "Theres no existing document detail for the existing request."
            );
        }

        return documentDetailMap;
    }

    private void setDocDetailMap(Map<Integer, Document> docDetailMap, List<DocumentRequest> userDocReqList, int barangayId){
        userDocReqList.forEach((docReq) -> {
            int documentId = docReq.documentId();
            Optional<Document> optionalDocDetail = documentDao.getOptionalDocument(barangayId, documentId);

            optionalDocDetail.ifPresent(
                    (docDetail) -> docDetailMap.put(documentId, docDetail)
            );
        });
    }

    protected CheckRequestStatus createCheckRequestStatus(DocumentRequest documentRequest){
        var checkReqStatusService = new CheckReqStatusService(this, documentRequestDao);
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

    protected UserDeleteRequest createUserDeleteRequest(RequestSelectContext requestSelectContext){
        var userDeleteReqService = new UserDeleteReqService(documentRequestDao);
        var userDeleteReqController = new UserDeleteReqController(
                userDeleteReqService, requestSelectContext
        );

        return new UserDeleteRequest(userDeleteReqController);
    }

    public boolean isRequestApproved(String referenceId){
        try {
            Optional<Boolean> optionalDecision = documentRequestDao.isRequestApproved(referenceId);

            return optionalDecision.orElseThrow(
                    () -> new NotFoundException("Request status not found! Please try again!")
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch request status with reference_id : " + referenceId);
        }
    }
}
