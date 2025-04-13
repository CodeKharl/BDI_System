package org.isu_std.dao;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.modify.ModifyDocManager;
import org.isu_std.models.Document;

public interface DocManageDao {
    boolean isDeleteSuccess(int barangayId, int documentId);
    boolean add(int barangayId, Document document);
    boolean modify(ModifyDocManager modifyDocManager);
}
