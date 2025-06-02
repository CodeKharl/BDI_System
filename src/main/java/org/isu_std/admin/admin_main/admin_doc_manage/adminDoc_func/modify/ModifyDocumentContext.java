package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.modify;

import org.isu_std.models.model_builders.DocumentBuilder;

public class ModifyDocumentContext {
    private final int barangayId;
    private int documentId;

    private String chosenDocAttributeName;
    private final DocumentBuilder documentBuilder;

    protected ModifyDocumentContext(int barangayId, DocumentBuilder documentBuilder){
        this.barangayId = barangayId;
        this.documentBuilder = documentBuilder;
    }

    public void setChosenDocAttributeName(String chosenDocumentAttribute) {
        this.chosenDocAttributeName = chosenDocumentAttribute;
    }

    public String getChosenDocAttributeName() {
        return chosenDocAttributeName;
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
