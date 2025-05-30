package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.modify;

import org.isu_std.models.Document;
import org.isu_std.models.model_builders.DocumentBuilder;

public class ModifyDocumentContext {
    private final int barangayId;
    private int documentId;

    private String documentDetail;
    private final DocumentBuilder documentBuilder;

    protected ModifyDocumentContext(int barangayId, DocumentBuilder documentBuilder){
        this.barangayId = barangayId;
        this.documentBuilder = documentBuilder;
    }

    public void setDocumentDetail(String documentDetail) {
        this.documentDetail = documentDetail;
    }

    public String getDocumentDetail() {
        return documentDetail;
    }

    protected void setDocumentId(int documentId){
        this.documentId = documentId;
    }

    public int getDocumentId(){
        return this.documentId;
    }

    public int getBarangayId(){
        return this.barangayId;
    }

    public DocumentBuilder getDocumentBuilder() {
        return documentBuilder;
    }
}
