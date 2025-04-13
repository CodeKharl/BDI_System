package org.isu_std.dao;

import org.isu_std.models.DocumentRequest;

import java.util.List;

public interface DocumentRequestDao {
    boolean addDocRequest(DocumentRequest documentRequest);
    List<DocumentRequest> getDocRequestPendingList(int barangayId);
    boolean setRequestApprove(String referenceId);
    boolean deleteDocRequest(String referenceId);
    int getUserDocRequestCount(DocumentRequest documentRequest);
}
