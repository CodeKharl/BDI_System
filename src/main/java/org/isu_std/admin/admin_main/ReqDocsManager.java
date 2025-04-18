package org.isu_std.admin.admin_main;

import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.UserPersonal;

public class ReqDocsManager {
    private final DocumentRequest documentRequest;
    private final UserPersonal userPersonal;
    private final Document document;

    public ReqDocsManager(DocumentRequest documentRequest, UserPersonal userPersonal, Document document){
        this.documentRequest = documentRequest;
        this.userPersonal = userPersonal;
        this.document = document;
    }

    public DocumentRequest getDocumentRequest() {
        return documentRequest;
    }

    public UserPersonal getUserPersonal() {
        return userPersonal;
    }

    public Document getDocument() {
        return document;
    }

    public int getUserId(){
        return this.documentRequest.userId();
    }

    public int getDocumentId(){
        return this.documentRequest.documentId();
    }

}
