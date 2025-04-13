package org.isu_std.admin.requested_documents.req_approve;

import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;

public class RequestApproveController {
    private final RequestApproveService requestApproveService;
    private final DocumentRequest documentRequest;

    public RequestApproveController(RequestApproveService requestApproveService, DocumentRequest documentRequest){
        this.requestApproveService = requestApproveService;
        this.documentRequest = documentRequest;
    }

    protected boolean isReqApproved(){
        try{
            requestApproveService.requestApprovePerformed(documentRequest.referenceId());
            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
