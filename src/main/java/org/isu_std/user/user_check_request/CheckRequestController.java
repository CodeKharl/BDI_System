package org.isu_std.user.user_check_request;

import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.models.Document;

import java.util.List;
import java.util.Map;

public class CheckRequestController {
    private final CheckRequestService checkRequestService;
    private final ReqInfoManager reqInfoManager;

    public CheckRequestController(CheckRequestService checkRequestService, int barangayId, int userId){
        this.checkRequestService = checkRequestService;
        this.reqInfoManager = checkRequestService.createReqInfoManager(barangayId, userId);
    }

    protected boolean isExistingDocMapSet(){
        try{
            List<Integer> documentIdList = checkRequestService.getDocumentIdList(
                    reqInfoManager.getUserId(),
                    reqInfoManager.getBarangayId()
            );

            setDocumentDetailMap(documentIdList);
            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void setDocumentDetailMap(List<Integer> docIdList) throws NotFoundException{
        Map<Integer, Document> documentDetailList = checkRequestService.getDocumentDetailMap(
                reqInfoManager.getBarangayId(), docIdList
        );

        reqInfoManager.setDocumentDetailMap(documentDetailList);
    }

    protected void printDocumentDetails(){
        Util.printSubSectionTitle("Existing Request (Document ID -> Document Name - Price - Requirements");

        Map<Integer, Document> documentDetailsMap = reqInfoManager.getDocumentDetailMap();
        documentDetailsMap.forEach((documentId, document) ->
            Util.printInformation(
                    "%d -> %s".formatted(documentId, document.getDetails())
            )
        );
    }
}
