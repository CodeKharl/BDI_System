package org.isu_std.admin.admin_main.requested_documents;

import java.io.File;
import java.util.List;

import org.isu_std.admin.admin_main.ReqDocsManager;
import org.isu_std.admin.admin_main.ReqDocsManagerFactory;
import org.isu_std.admin.admin_main.req_files_view.ReqFilesViewFactory;
import org.isu_std.admin.admin_main.requested_documents.req_approve.RequestApprove;
import org.isu_std.admin.admin_main.requested_documents.req_approve.RequestApproveController;
import org.isu_std.admin.admin_main.requested_documents.req_approve.RequestApproveService;
import org.isu_std.admin.admin_main.req_files_view.RequirementFilesView;
import org.isu_std.admin.admin_main.requested_documents.req_decline.RequestDecline;
import org.isu_std.admin.admin_main.requested_documents.req_decline.RequestDeclineController;
import org.isu_std.admin.admin_main.requested_documents.req_decline.RequestDeclineService;
import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;

public class RequestedDocumentService {
    private final DocumentRequestDao documentRequestDao;
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;

    protected RequestedDocumentService(DocumentRequestDao documentRequestDao, DocumentDao documentDao, UserPersonalDao userPersonalDao){
        this.documentRequestDao = documentRequestDao;
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
    }

    protected List<DocumentRequest> getDocumentReqList(int barangayId){
        List<DocumentRequest> documentReqList = documentRequestDao.getDocRequestPendingList(barangayId);

        if(documentReqList.isEmpty()){
            throw new NotFoundException("There are currently no pending document requests.");
        }

        return documentReqList;
    }

    protected ReqDocsManager getReDocsManager(DocumentRequest documentRequest) throws OperationFailedException{
        ReqDocsManagerFactory reqDocsManagerFactory = new ReqDocsManagerFactory(documentDao, userPersonalDao);
        return reqDocsManagerFactory.createReDocsManager(documentRequest);
    }

    protected RequirementFilesView getReqFilesView(List<File> requirmentFileList){
        return ReqFilesViewFactory.createReqFilesView(requirmentFileList);
    }

    protected RequestApprove createRequestApprove(ReqDocsManager reqDocsManager){
        RequestApproveService requestApproveService = new RequestApproveService(documentRequestDao);
        RequestApproveController requestApproveController = new RequestApproveController(
                requestApproveService, reqDocsManager
        );

        return new RequestApprove(requestApproveController);
    }

    protected RequestDecline createRequestDecline(DocumentRequest documentRequest){
        RequestDeclineService requestDeclineService = new RequestDeclineService(documentRequestDao);
        RequestDeclineController requestDeclineController = new RequestDeclineController(
                requestDeclineService, documentRequest
        );

        return new RequestDecline(requestDeclineController);
    }
}
