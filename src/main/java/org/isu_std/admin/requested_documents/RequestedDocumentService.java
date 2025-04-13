package org.isu_std.admin.requested_documents;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.isu_std.admin.requested_documents.req_approve.RequestApprove;
import org.isu_std.admin.requested_documents.req_approve.RequestApproveController;
import org.isu_std.admin.requested_documents.req_approve.RequestApproveService;
import org.isu_std.admin.requested_documents.req_files_view.ReqFilesViewController;
import org.isu_std.admin.requested_documents.req_files_view.ReqFilesViewService;
import org.isu_std.admin.requested_documents.req_files_view.RequirementFilesView;
import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
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
        List<DocumentRequest> documentReqList = documentRequestDao.getDocRequestPendingList(barangayId);

        if(documentReqList.isEmpty()){
            throw new NotFoundException("There are currently no pending document requests.");
        }

        return documentReqList;
    }

    protected ReqDocsManager createReDocsManager(DocumentRequest documentRequest) throws OperationFailedException{
        UserPersonal userPersonal = getUserPersonal(documentRequest.userId());
        Document document = getDocument(documentRequest.documentId());

        return new ReqDocsManager(documentRequest, userPersonal, document);
    }

    private UserPersonal getUserPersonal(int userId){
        Optional<UserPersonal> optionalUserPersonal = userPersonalDao.getOptionalUserPersonal(userId);
        return optionalUserPersonal.orElseThrow(
                () -> new OperationFailedException("Failed to get the informations of the user.")
        );
    }

    private Document getDocument(int docId){
        Optional<Document> optionalDocument = documentDao.getOptionalDocument(docId);
        return optionalDocument.orElseThrow(
                () -> new OperationFailedException("Failed to get the requested document.")
        );
    }

    protected RequirementFilesView createReqFilesView(List<File> requirmentFileList){
        ReqFilesViewService reqFilesViewService = new ReqFilesViewService();
        ReqFilesViewController reqFilesViewController = new ReqFilesViewController(
                reqFilesViewService, requirmentFileList
        );

        return new RequirementFilesView(reqFilesViewController);
    }

    protected RequestApprove createRequestApprove(DocumentRequest documentRequest){
        RequestApproveService requestApproveService = new RequestApproveService(documentRequestDao);
        RequestApproveController requestApproveController = new RequestApproveController(
                requestApproveService, documentRequest
        );

        return new RequestApprove(requestApproveController);
    }
}
