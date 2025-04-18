package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;

import java.util.List;

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
