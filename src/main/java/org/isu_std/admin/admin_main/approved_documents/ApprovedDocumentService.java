package org.isu_std.admin.admin_main.approved_documents;

import org.isu_std.admin.admin_main.ReqDocsManager;
import org.isu_std.admin.admin_main.ReqDocsManagerBuilder;
import org.isu_std.admin.admin_main.ReqDocsManagerFactory;
import org.isu_std.admin.admin_main.approved_documents.approved_documents_export.ApprovedDocExport;
import org.isu_std.admin.admin_main.approved_documents.approved_documents_export.ApprovedDocExportController;
import org.isu_std.admin.admin_main.approved_documents.approved_documents_export.ApprovedDocExportService;
import org.isu_std.admin.admin_main.req_files_view.ReqFilesViewFactory;
import org.isu_std.admin.admin_main.req_files_view.RequirementFilesView;
import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.io.file_setup.FileChooser;
import org.isu_std.io.folder_setup.FolderConfig;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.Payment;
import org.isu_std.models.UserPersonal;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class ApprovedDocumentService {
    private final DocumentRequestDao documentRequestDao;
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;
    private final PaymentDao paymentDao;

    protected ApprovedDocumentService(DocumentRequestDao documentRequestDao, DocumentDao documentDao, UserPersonalDao userPersonalDao, PaymentDao paymentDao){
        this.documentRequestDao = documentRequestDao;
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
        this.paymentDao = paymentDao;
    }

    protected List<DocumentRequest> getApproveDocList(int barangayId){
        List<DocumentRequest> approvedDodList = documentRequestDao.getApprovedDocList(barangayId);

        if(approvedDodList.isEmpty()){
            throw new NotFoundException("Theres no existing approved request!");
        }

        return approvedDodList;
    }

    protected ReqDocsManager getReqDocsManager(DocumentRequest documentRequest) throws OperationFailedException {
        ReqDocsManagerBuilder reqDocsManagerBuilder = new ReqDocsManagerBuilder(documentRequest);
        UserPersonal userPersonal = ReqDocsManagerFactory.getUserPersonal(userPersonalDao, documentRequest.userId());
        Document document = ReqDocsManagerFactory.getDocument(
                documentDao, documentRequest.barangayId(), documentRequest.documentId()
        );

        Payment payment = ReqDocsManagerFactory.getPayment(paymentDao, documentRequest.referenceId());

        reqDocsManagerBuilder
                .userPersonal(userPersonal)
                .document(document)
                .payment(payment);

        return reqDocsManagerBuilder.build();
    }

    protected RequirementFilesView getReqFilesView(List<File> requirementFiles){
        return ReqFilesViewFactory.createReqFilesView(requirementFiles);
    }

    protected File getApprovedDocFile(ReqDocsManager reqDocsManager){
        String filePath = FolderConfig.DOC_APPROVE_PATH.getPath();
        String fileName = reqDocsManager.documentRequest().referenceId() +
                reqDocsManager.document().documentFile().getName();

        File outputFile = new File("%s_%s".formatted(filePath, fileName));

        if(!outputFile.exists()){
            throw new NotFoundException("Theres no existing approved document file!");
        }

        return outputFile;
    }

    protected void checkPayment(Payment payment){
        if(payment == null){
            throw new NotFoundException("Theres no existing payment information of this approved request!");
        }
    }

    protected ApprovedDocExport createApprovedDocExport(ReqDocsManager reqDocsManager, File outputDocumentFile){
        ApprovedDocExportService approvedDocExportService = new ApprovedDocExportService();
        ApprovedDocExportController approvedDocExportController = new ApprovedDocExportController(
                approvedDocExportService, reqDocsManager, outputDocumentFile
        );

        return new ApprovedDocExport(approvedDocExportController);
    }

    protected void deleteApprovedRequestDocs(DocumentRequest documentRequest){
        if(!documentRequestDao.deleteDocRequest(documentRequest)){
            throw new OperationFailedException(
                    "Failed to delete the approved request document. Request can see but failed to process."
            );
        }
    }

    protected void openDocFile(File file){
        FileChooser.openFile(file);
    }
}
