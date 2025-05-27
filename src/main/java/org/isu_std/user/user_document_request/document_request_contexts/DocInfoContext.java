package org.isu_std.user.user_document_request.document_request_contexts;

import org.isu_std.models.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DocInfoContext {
    private final Map<Integer, Document> barangayDocumentsMap;
    private final Integer[] documentKeys;

    public DocInfoContext(@NotNull Map<Integer, Document> brgyDocsMap){
        this.barangayDocumentsMap = brgyDocsMap;

        this.documentKeys = brgyDocsMap
                .keySet()
                .toArray(new Integer[]{});
    }

    public Map<Integer, Document> getBarangayDocumentsMap() {
        return barangayDocumentsMap;
    }

    public Integer[] getDocumentKeys() {
        return documentKeys;
    }
}
