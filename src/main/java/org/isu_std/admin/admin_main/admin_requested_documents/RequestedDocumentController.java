package org.isu_std.admin.admin_main.admin_requested_documents;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.admin.admin_main.admin_requested_documents.req_approve.RequestApprove;
import org.isu_std.admin.admin_main.req_files_view.RequirementFilesView;
import org.isu_std.admin.admin_main.admin_requested_documents.req_decline.RequestDecline;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Barangay;
import org.isu_std.models.DocumentRequest;

import java.util.List;

public class RequestedDocumentController {
    private final RequestedDocumentService reqDocService;
    private final Barangay barangay;

    private List<DocumentRequest> documentRequestList;
    private RequestDocumentContext requestDocumentContext;

    protected RequestedDocumentController(RequestedDocumentService reqDocService, Barangay barangay){
        this.reqDocService = reqDocService;
        this.barangay = barangay;
    }

    protected boolean isThereExistingRequest(){
        try{
            documentRequestList = reqDocService.getDocumentReqList(barangay.barangayId());

            return true;
        }catch (ServiceException | NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printRequestedDocs(){
        Util.printSubSectionTitle("Pending Document Requests (IDs)");
        Util.printInformation("User ID - Document ID");
        Util.printListWithCount(documentRequestList);
    }

    protected boolean setDocumentReqChoice(int docChoice){
        try{
            int index = docChoice - 1;
            DocumentRequest documentRequest = documentRequestList.get(index);

            this.requestDocumentContext = reqDocService.getReqDocsContext(documentRequest);

            return true;
        }catch (ServiceException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected String getDocReqSectionTitle(){
        return "Document Request View -> (User ID - %d | Document ID - %d)"
                .formatted(requestDocumentContext.getUserId(), requestDocumentContext.getDocumentId());
    }

    protected boolean isReqValidationFinish(int choice){
        switch(choice){
            case 1 -> {
                return requestApprove();
            }
            case 2 -> {
                return requestDecline();
            }

            case 3 -> requestDocumentContext.document().printDetailsWithDocumentFile();
            case 4 -> requestDocumentContext.userPersonal().printPersonalStats();
            case 5 -> requirementFileView();
        }

        return false;
    }

    protected void requirementFileView(){
        RequirementFilesView requirementFilesView = reqDocService
                .getReqFilesView(requestDocumentContext.documentRequest().requirementDocList());

        requirementFilesView.viewProcess();
    }

    protected boolean requestApprove(){
        RequestApprove requestApprove = reqDocService
                .createRequestApprove(requestDocumentContext);

        return requestApprove.requestApprovePerformed();
    }

    protected boolean requestDecline(){
        RequestDecline requestDecline = reqDocService
                .createRequestDecline(requestDocumentContext.documentRequest());

        return requestDecline.requestDeclinePerformed();
    }

    protected int getReqDocListLength(){
        return this.documentRequestList.size();
    }
}
