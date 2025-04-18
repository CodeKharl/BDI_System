package org.isu_std.admin.admin_main.approved_documents;

import org.isu_std.admin.admin_main.ReqDocsManager;
import org.isu_std.admin.admin_main.ReqDocsManagerFactory;
import org.isu_std.admin.admin_main.req_files_view.ReqFilesViewFactory;
import org.isu_std.admin.admin_main.req_files_view.RequirementFilesView;
import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;

import java.io.File;
import java.util.List;

public class ApprovedService {
    private final DocumentRequestDao documentRequestDao;
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;

    protected ApprovedService(DocumentRequestDao documentRequestDao, DocumentDao documentDao, UserPersonalDao userPersonalDao){
        this.documentRequestDao = documentRequestDao;
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
    }

    protected List<DocumentRequest> getApproveDocList(int barangayId){
        List<DocumentRequest> approvedDodList = documentRequestDao.getApprovedDocList(barangayId);

        if(approvedDodList.isEmpty()){
            throw new NotFoundException("Theres no existing approved request!");
        }

        return approvedDodList;
    }

    protected ReqDocsManager getReqDocsManager(DocumentRequest documentRequest) throws OperationFailedException {
        ReqDocsManagerFactory reqDocsManagerFactory = new ReqDocsManagerFactory(documentDao, userPersonalDao);
        return reqDocsManagerFactory.createReDocsManager(documentRequest);
    }

    protected RequirementFilesView getReqFilesView(List<File> requirementFiles){
        return ReqFilesViewFactory.createReqFilesView(requirementFiles);
    }
}
