package org.isu_std.admin.admin_main.approved_documents;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.admin.admin_main.ReqDocsManagerBuilder;
import org.isu_std.admin.admin_main.ReqDocsManagerProvider;
import org.isu_std.admin.admin_main.approved_documents.approved_doc_confirm_export.ApprovedDocExport;
import org.isu_std.admin.admin_main.approved_documents.approved_doc_confirm_export.ApprovedDocExportController;
import org.isu_std.admin.admin_main.approved_documents.approved_doc_confirm_export.ApprovedDocExportService;
import org.isu_std.admin.admin_main.req_files_view.ReqFilesViewFactory;
import org.isu_std.admin.admin_main.req_files_view.RequirementFilesView;
import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.doc_output_file_provider.DocOutFileManager;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.file_setup.FileChooser;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.Payment;
import org.isu_std.models.UserPersonal;

import java.io.File;
import java.util.List;

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

    protected RequestDocumentContext getRequestDocContext(DocumentRequest documentRequest)
            throws OperationFailedException {
        var reqDocsManagerBuilder = new ReqDocsManagerBuilder(documentRequest);
        var reqDocsManagerProvider = new ReqDocsManagerProvider(
                userPersonalDao, documentDao, paymentDao
        );

        try {
            UserPersonal userPersonal = reqDocsManagerProvider
                    .getUserPersonal(documentRequest.userId());

            Document document = reqDocsManagerProvider
                    .getDocument(documentRequest.barangayId(), documentRequest.documentId());

            Payment payment = reqDocsManagerProvider
                    .getPayment(documentRequest.referenceId());

            reqDocsManagerBuilder
                    .userPersonal(userPersonal)
                    .document(document)
                    .payment(payment);

            return reqDocsManagerBuilder.build();
        }catch (NotFoundException e){
            throw new OperationFailedException(
                    "Failed to get the approved request information", e
            );
        }
    }

    protected RequirementFilesView getReqFilesView(List<File> requirementFiles){
        return ReqFilesViewFactory.createReqFilesView(requirementFiles);
    }

    protected File getApprovedDocFile(RequestDocumentContext requestDocumentContext){
        String outputDocFilePathName = DocOutFileManager
                .getOutputDocFilePathName(requestDocumentContext);

        File outputFile = new File(outputDocFilePathName);

        if(outputFile.exists()){
            return outputFile;
        }

        throw new NotFoundException("Theres no existing approved document file!");
    }

    protected void checkPayment(Payment payment){
        if(payment == null){
            throw new NotFoundException(
                    "Theres no existing payment information of this approved request!"
            );
        }
    }

    protected ApprovedDocExport createApprovedDocExport(RequestDocumentContext requestDocumentContext, File outputDocumentFile){
        ApprovedDocExportService approvedDocExportService = new ApprovedDocExportService(documentRequestDao);
        ApprovedDocExportController approvedDocExportController = new ApprovedDocExportController(
                approvedDocExportService, requestDocumentContext, outputDocumentFile
        );

        return new ApprovedDocExport(approvedDocExportController);
    }

    protected void openDocFile(File file){
        FileChooser.openFile(file);
    }
}
