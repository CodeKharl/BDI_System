package org.isu_std.admin.admin_main.requested_documents;

import java.io.File;
import java.util.List;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.admin.admin_main.ReqDocsManagerBuilder;
import org.isu_std.admin.admin_main.ReqDocsManagerProvider;
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
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.UserPersonal;

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
        List<DocumentRequest> documentReqList = documentRequestDao.getBrgyDocReqPendingList(barangayId);

        if(documentReqList.isEmpty()){
            throw new NotFoundException("There are currently no pending document requests.");
        }

        return documentReqList;
    }

    protected RequestDocumentContext getReqDocsContext(DocumentRequest documentRequest) throws OperationFailedException{
        var reqDocsManagerBuilder = new ReqDocsManagerBuilder(documentRequest);
        var reqDocsManagerProvider = new ReqDocsManagerProvider(
                userPersonalDao, documentDao, null
        );

        try {
            UserPersonal userPersonal = reqDocsManagerProvider
                    .getUserPersonal(documentRequest.userId());

            Document document = reqDocsManagerProvider
                    .getDocument(documentRequest.barangayId(), documentRequest.documentId());

            reqDocsManagerBuilder
                    .userPersonal(userPersonal)
                    .document(document);

            return reqDocsManagerBuilder.build();
        }catch (NotFoundException e){
            throw new OperationFailedException("Failed to get the requested information!", e);
        }
    }

    protected RequirementFilesView getReqFilesView(List<File> requirmentFileList){
        return ReqFilesViewFactory.createReqFilesView(requirmentFileList);
    }

    protected RequestApprove createRequestApprove(RequestDocumentContext requestDocumentContext){
        var requestApproveService = new RequestApproveService(documentRequestDao);
        var requestApproveController = new RequestApproveController(
                requestApproveService, requestDocumentContext
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
