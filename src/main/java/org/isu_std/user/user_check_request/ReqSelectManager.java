package org.isu_std.user.user_check_request;

import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;

public class ReqSelectManager {
    private DocumentRequest selectedDocRequest;
    private Document selectedDocument;

    protected ReqSelectManager(){}

    protected void setSelectedDocRequest(DocumentRequest selectedDocRequest){
        this.selectedDocRequest = selectedDocRequest;
    }

    protected void setSelectedDocument(Document selectedDocument){
        this.selectedDocument = selectedDocument;
    }

    protected DocumentRequest getSelectedDocRequest(){
        return this.selectedDocRequest;
    }

    protected Document getSelectedDocument(){
        return this.selectedDocument;
    }
}
