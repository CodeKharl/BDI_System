package org.isu_std.dao;

import org.isu_std.models.Document;

import java.util.Map;
import java.util.Optional;

public interface DocumentDao {
    Map<Integer, Document> getDocumentMap(int barangayId);
    Optional<String> getDocumentName(int barangayId, int documentId);
    Optional<Document> getOptionalDocument(int barangayId, int documentId);
    double getDocumentPrice(int barangayId, int documentId);
}
