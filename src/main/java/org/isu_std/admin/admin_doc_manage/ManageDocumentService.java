package org.isu_std.admin.admin_doc_manage;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.AdminDocFuncFactory;

public class ManageDocumentService {
    private final AdminDocFuncFactory adminDocFuncFactory;

    public ManageDocumentService(AdminDocFuncFactory adminDocFuncFactory){
        this.adminDocFuncFactory = adminDocFuncFactory;
    }

    protected AdminDocumentImpl getDocumentFunctions(int choice){
        return adminDocFuncFactory.createDocumentManage(choice);
    }
}
