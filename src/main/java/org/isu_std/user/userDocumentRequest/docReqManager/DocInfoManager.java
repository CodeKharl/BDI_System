package org.isu_std.user.userDocumentRequest.docReqManager;

import org.isu_std.models.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DocInfoManager {
    private final Map<Integer, Document> barangayDocumentsMap;
    private final Integer[] documentKeys;

    public DocInfoManager(@NotNull Map<Integer, Document> barangayDocumentsMap){
        this.barangayDocumentsMap = barangayDocumentsMap;

        this.documentKeys = barangayDocumentsMap
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
