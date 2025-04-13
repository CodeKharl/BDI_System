package org.isu_std.admin.admin_doc_manage.adminDoc_func.others;

public enum DocumentConfig {
    MIN_DOCNAME_LENGTH(5 );

    private final int value;

    DocumentConfig(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
