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
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
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
        try {
            List<DocumentRequest> documentReqList = documentRequestDao
                    .getBrgyDocReqPendingList(barangayId);

            if (documentReqList.isEmpty()) {
                throw new NotFoundException("There are currently no pending document requests.");
            }

            return documentReqList;
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException(
                    "Failed to fetch document request list with barangay_id : " + barangayId
            );
        }
    }

    protected RequestDocumentContext getReqDocsContext(DocumentRequest documentRequest){
        var reqDocsManagerBuilder = new ReqDocsManagerBuilder(documentRequest);

        try {
            setReqDocsManagerBuilder(reqDocsManagerBuilder, documentRequest);

            return reqDocsManagerBuilder.build();
        }catch (DataAccessException | NotFoundException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException(
                    "Failed to setup document request with : " + documentRequest
            );
        }
    }

    private void setReqDocsManagerBuilder(
            ReqDocsManagerBuilder reqDocsManagerBuilder,
            DocumentRequest documentRequest
    ){
        var reqDocsManagerProvider = new ReqDocsManagerProvider(
                userPersonalDao, documentDao, null
        );

        UserPersonal userPersonal = reqDocsManagerProvider
                .getUserPersonal(documentRequest.userId());

        Document document = reqDocsManagerProvider
                .getDocument(documentRequest.barangayId(), documentRequest.documentId());

        reqDocsManagerBuilder
                .userPersonal(userPersonal)
                .document(document);
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
        var requestDeclineService = new RequestDeclineService(documentRequestDao);
        var requestDeclineController = new RequestDeclineController(
                requestDeclineService, documentRequest
        );

        return new RequestDecline(requestDeclineController);
    }
}
