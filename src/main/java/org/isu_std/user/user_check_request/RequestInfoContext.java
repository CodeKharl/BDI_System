package org.isu_std.user.user_check_request;

import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.User;

import java.util.List;
import java.util.Map;

public class RequestInfoContext {
    private final User user;

    private List<DocumentRequest> userDocRequestMap;
    private Map<Integer, Document> documentDetailMap;

    public RequestInfoContext(User user){
        this.user = user;
    }

    public void setRefWithDocIDMap(List<DocumentRequest> userDocRequestMap){
        this.userDocRequestMap = userDocRequestMap;
    }

    public List<DocumentRequest> getUserDocRequestList(){
        return this.userDocRequestMap;
    }

    public void setDocumentDetailMap(Map<Integer, Document> documentDetailMap){
        this.documentDetailMap = documentDetailMap;
    }

    public Map<Integer, Document> getDocumentDetailMap(){
        return this.documentDetailMap;
    }

    public User getUser(){
        return this.user;
    }

}
