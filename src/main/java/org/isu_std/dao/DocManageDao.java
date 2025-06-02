package org.isu_std.dao;

import org.isu_std.models.Document;

public interface DocManageDao {
    boolean deleteDocument(int barangayId, int documentId);
    boolean addDocument(int barangayId, Document document);
    boolean updateDocument(String chosenAttributeName, Document document, int barangayId, int userId);
}
