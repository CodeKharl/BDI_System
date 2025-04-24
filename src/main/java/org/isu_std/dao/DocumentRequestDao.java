package org.isu_std.dao;

import org.isu_std.models.DocumentRequest;

import java.util.List;

public interface DocumentRequestDao {
    boolean addDocRequest(DocumentRequest documentRequest);
    List<DocumentRequest> getBrgyDocReqPendingList(int barangayId);
    List<DocumentRequest> getApprovedDocList(int barangayId);
    boolean setRequestApprove(String referenceId);
    boolean deleteDocRequest(DocumentRequest documentRequest);
    int getUserDocRequestCount(DocumentRequest documentRequest);
    List<Integer> getUserDocIdPendingList(int userId, int barangayId);
}
