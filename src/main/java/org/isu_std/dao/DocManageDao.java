package org.isu_std.dao;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.modify.ModifyDocumentContext;
import org.isu_std.models.Document;

public interface DocManageDao {
    boolean deleteDocument(int barangayId, int documentId);
    boolean addDocument(int barangayId, Document document);
    boolean updateDocument(ModifyDocumentContext modifyDocumentContext);
}
