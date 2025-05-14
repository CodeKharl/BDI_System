package org.isu_std.admin.admin_doc_manage.adminDoc_func.others;

import org.isu_std.dao.DocumentDao;

public class DocFileDeletionFactory {
    private DocFileDeletionFactory(){}

    public static DocFileDeletion createDocFileDeletion(DocumentDao documentDao){
        return new DocFileDeletion(documentDao);
    }
}
