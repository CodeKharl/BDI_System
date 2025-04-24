package org.isu_std.user.user_check_request;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.models.Document;

import java.util.*;

public class CheckRequestService {
    private final DocumentRequestDao documentRequestDao;
    private final DocumentDao documentDao;

    public CheckRequestService(DocumentRequestDao documentRequestDao, DocumentDao documentDao){
        this.documentRequestDao = documentRequestDao;
        this.documentDao = documentDao;
    }

    protected ReqInfoManager createReqInfoManager(int barangayId, int userId){
        return new ReqInfoManager(barangayId, userId);
    }

    protected List<Integer> getDocumentIdList(int userId, int barangayId){
        List<Integer> documentIdList = documentRequestDao.getUserDocIdPendingList(userId, barangayId);

        if(documentIdList.isEmpty()){
            throw new NotFoundException("Theres no existing request found!");
        }

        return documentIdList;
    }

    protected Map<Integer, Document> getDocumentDetailMap(int barangayId, List<Integer> documentIdList){
        Map<Integer, Document> documentDetailList = new HashMap<>();

        for(Integer documentId : documentIdList){
            Optional<Document> optionalDocDetail = documentDao.getOptionalDocDetail(barangayId, documentId);
            optionalDocDetail.ifPresent(
                    (docDetail) -> documentDetailList.put(documentId, docDetail)
            );
        }

        if(documentDetailList.isEmpty()){
            throw new NotFoundException("Theres no existing document detail for the existing request.");
        }

        return documentDetailList;
    }
}
