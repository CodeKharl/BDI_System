package org.isu_std.admin.admin_doc_manage.adminDoc_func.modify;

import org.isu_std.models.modelbuilders.DocumentBuilder;

public class ModifyDocManager {
    private final int barangayId;
    private int documentId;
    private String documentDetail;
    private final DocumentBuilder documentBuilder;

    protected ModifyDocManager(int barangayId, DocumentBuilder documentBuilder){
        this.barangayId = barangayId;
        this.documentBuilder = documentBuilder;
    }

    public String getDocumentDetail() {
        return documentDetail;
    }

    public void setDocumentDetail(String documentDetail) {
        this.documentDetail = documentDetail;
    }

    public DocumentBuilder getDocumentBuilder() {
        return documentBuilder;
    }

    protected void setDocumentId(int documentId){
        this.documentId = documentId;
    }

    public int getBarangayId(){
        return this.barangayId;
    }

    public int getDocumentId(){
        return this.documentId;
    }
}
