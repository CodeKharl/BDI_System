package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.others;

public enum DocumentManageCodes {
    ADDING('A'), DELETING('D'),
    MODIFYING('M'), CANCELING('C'),
    DOCUMENT_INFO(new String[]{
            "Document_Name", "Price", "Requirements", "Document_File"
    });

    private char value;
    private String[] arrValue;

    DocumentManageCodes(char value){
        this.value = value;
    }

    DocumentManageCodes(String[] arrValue){
        this.arrValue = arrValue;
    }

    public char getCode(){
        return this.value;
    }

    public String[] getArrCode(){
        return this.arrValue;
    }
}
