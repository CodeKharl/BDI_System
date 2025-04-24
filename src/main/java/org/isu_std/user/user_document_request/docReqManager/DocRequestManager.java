package org.isu_std.user.user_document_request.docReqManager;

import org.isu_std.models.Document;

import java.io.File;
import java.util.List;

public class DocRequestManager {
    private int documentId;
    private Document selectedDocument;
    private List<File> docRequirementFiles;

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public void setRequirements(List<File> docRequirementFiles) {
        this.docRequirementFiles = docRequirementFiles;
    }

    public List<File> getDocRequirementFiles(){
        return this.docRequirementFiles;
    }

    public Document getDocument() {
        return selectedDocument;
    }

    public void setDocRequest(int documentId, Document document){
        this.documentId = documentId;
        this.selectedDocument = document;
    }
}
