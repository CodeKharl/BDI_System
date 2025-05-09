package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;

import java.io.IOException;

public class RequestApproveController {
    private final RequestApproveService requestApproveService;
    private final RequestDocumentContext requestDocumentContext;

    public RequestApproveController(RequestApproveService requestApproveService, RequestDocumentContext requestDocumentContext){
        this.requestApproveService = requestApproveService;
        this.requestDocumentContext = requestDocumentContext;
    }

    protected boolean isReqApproved(){
        try{
            requestApproveService.createOutputDocumentFile(requestDocumentContext);
            requestApproveService.requestApprovePerformed(requestDocumentContext);
            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
