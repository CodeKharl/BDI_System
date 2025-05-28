package org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_file_deletion;

import org.isu_std.dao.DocumentDao;

public class DocFileDeletionFactory {
    private DocFileDeletionFactory(){}

    public static DocumentFileDeletion createDocFileDeletion(DocumentDao documentDao){
        return new DocumentFileDeletion(documentDao);
    }
}
