package org.isu_std.user.user_check_request;

import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;

public class ReqSelectManager {
    private DocumentRequest selectedDocRequest;
    private Document selectedDocument;

    protected ReqSelectManager(){}

    public void setSelectedDocRequest(DocumentRequest selectedDocRequest){
        this.selectedDocRequest = selectedDocRequest;
    }

    public void setSelectedDocument(Document selectedDocument){
        this.selectedDocument = selectedDocument;
    }

    public DocumentRequest getSelectedDocRequest(){
        return this.selectedDocRequest;
    }

    public Document getSelectedDocument(){
        return this.selectedDocument;
    }
}
