package org.isu_std.admin.admin_main.admin_requested_documents.req_approve;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;

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
        }catch (ServiceException | OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
