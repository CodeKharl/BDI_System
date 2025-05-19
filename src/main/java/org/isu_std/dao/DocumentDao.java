package org.isu_std.dao;

import org.isu_std.models.Document;

import java.util.Map;
import java.util.Optional;

public interface DocumentDao {
    Map<Integer, Document> getDocumentMap(int barangayId);
    Optional<String> findDocumentName(int barangayId, int documentId);
    Optional<Document> getOptionalDocument(int barangayId, int documentId);
    Optional<Double> findDocumentPrice(int barangayId, int documentId);
    Optional<String> findDocumentFileName(int barangayId, int documentId);
}
