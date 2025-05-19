package org.isu_std.dao;

import org.isu_std.models.DocumentRequest;

import java.util.List;
import java.util.Optional;

public interface DocumentRequestDao {
    boolean addDocRequest(DocumentRequest documentRequest);
    List<DocumentRequest> getBrgyDocReqPendingList(int barangayId);
    List<DocumentRequest> getApprovedDocList(int barangayId);
    boolean requestApprove(String referenceId);
    Optional<Boolean> isRequestApproved(String referenceId);
    boolean deleteDocRequest(String referenceId);
    Optional<Integer> getUserDocRequestCount(DocumentRequest documentRequest);
    List<DocumentRequest> getUserReqDocList(int userId, int barangayId);
}
