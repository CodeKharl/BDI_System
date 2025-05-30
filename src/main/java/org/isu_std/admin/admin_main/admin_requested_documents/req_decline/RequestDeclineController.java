package org.isu_std.admin.admin_main.admin_requested_documents.req_decline;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.DocumentRequest;

public class RequestDeclineController {
    private final RequestDeclineService requestDeclineService;
    private final DocumentRequest documentRequest;

    public RequestDeclineController(RequestDeclineService requestDeclineService, DocumentRequest documentRequest){
        this.requestDeclineService = requestDeclineService;
        this.documentRequest = documentRequest;
    }

    protected boolean isRequestRemovedSuccess(){
        try{
            requestDeclineService.deleteRequestPerformed(documentRequest);
            return true;
        }catch (ServiceException | OperationFailedException e){
            Util.printMessage(e.getMessage());
        }

        return false;
    }
}
