package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.admin.admin_main.ReqDocsManager;
import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;

import java.io.IOException;
import java.util.List;

public class RequestApproveController {
    private final RequestApproveService requestApproveService;
    private final ReqDocsManager reqDocsManager;

    public RequestApproveController(RequestApproveService requestApproveService, ReqDocsManager reqDocsManager){
        this.requestApproveService = requestApproveService;
        this.reqDocsManager = reqDocsManager;
    }

    protected boolean isReqApproved(){
        try{
            requestApproveService.requestApprovePerformed(reqDocsManager);
            return true;
        }catch (IOException | OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
