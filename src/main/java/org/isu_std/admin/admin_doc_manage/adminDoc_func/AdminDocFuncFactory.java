package org.isu_std.admin.admin_doc_manage.adminDoc_func;

import org.isu_std.admin.admin_doc_manage.AdminDocumentImpl;

import java.util.Map;

public class AdminDocFuncFactory {
    private final Map<Integer, AdminDocumentImpl> manageMap;

    public AdminDocFuncFactory(Map<Integer, AdminDocumentImpl> manageMap){
        this.manageMap = manageMap;
    }

    public AdminDocumentImpl createDocumentManage(int choice){
        return manageMap.getOrDefault(choice, null);
    }
}
