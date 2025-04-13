package org.isu_std.admin.requested_documents;

import org.isu_std.admin.requested_documents.req_approve.RequestApprove;
import org.isu_std.admin.requested_documents.req_files_view.RequirementFilesView;
import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Barangay;
import org.isu_std.models.DocumentRequest;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestedDocumentController {
    private final RequestedDocumentService reqDocService;
    private final Barangay barangay;
    private List<DocumentRequest> documentRequestList;
    private ReqDocsManager reqDocsManager;

    protected RequestedDocumentController(RequestedDocumentService reqDocService, Barangay barangay){
        this.reqDocService = reqDocService;
        this.barangay = barangay;
    }

    protected boolean isThereExistingRequest(){
        try{
            documentRequestList = reqDocService.getDocumentReqList(barangay.barangayId());
            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printRequestedDocs(){
        Util.printSectionTitle("Pending Document Requests (IDs)");
        Util.printInformation("User ID - Document ID");

        AtomicInteger count = new AtomicInteger();
        documentRequestList.forEach((documentRequest) -> Util.printChoices(
                    "%d. %s".formatted(count.getAndIncrement() + 1, documentRequest.getDocIdWithUserId())
            )
        );
    }

    protected boolean setDocumentReqChoice(int docChoice){
        try{
            this.reqDocsManager = reqDocService
                    .createReDocsManager(documentRequestList.get(docChoice - 1));

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected String getDocReqSubTitle(){
        return "Document Request View -> (User ID - %d | Document ID - %d)"
                .formatted(reqDocsManager.getUserId(), reqDocsManager.getDocumentId());
    }

    protected boolean isRequestFinishProcess(int choice){
        switch(choice){
            case 1 -> requestApprove();
            case 2 -> reqDocsManager.getDocument().printlnStats();
            case 3 -> reqDocsManager.getUserPersonal().printPersonalStats();
            case 4 -> requirementFileView();
        }

        return false;
    }

    protected void requirementFileView(){
        RequirementFilesView requirementFilesView = reqDocService.createReqFilesView(
                reqDocsManager.getDocumentRequest().requirementDocList()
        );

        requirementFilesView.viewProcess();
    }

    protected void requestApprove(){
        RequestApprove requestApprove = reqDocService.createRequestApprove(
                reqDocsManager.getDocumentRequest()
        );

        requestApprove.RequestApprovePerformed();
    }

    protected int getReqDocListLength(){
        return this.documentRequestList.size();
    }
}
