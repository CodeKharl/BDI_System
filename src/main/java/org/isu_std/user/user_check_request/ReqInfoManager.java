package org.isu_std.user.user_check_request;

import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;

import java.util.List;
import java.util.Map;

public class ReqInfoManager {
    private final int barangayId;
    private final int userId;

    private List<DocumentRequest> userDocRequestMap;
    private Map<Integer, Document> documentDetailMap;

    public ReqInfoManager(int barangayId, int userId){
        this.barangayId = barangayId;
        this.userId = userId;
    }

    public void setRefWithDocIDMap(List<DocumentRequest> userDocRequestMap){
        this.userDocRequestMap = userDocRequestMap;
    }

    public List<DocumentRequest> getUserDocRequestMap(){
        return this.userDocRequestMap;
    }

    public void setDocumentDetailMap(Map<Integer, Document> documentDetailMap){
        this.documentDetailMap = documentDetailMap;
    }

    public Map<Integer, Document> getDocumentDetailMap(){
        return this.documentDetailMap;
    }

    public int getBarangayId(){
        return this.barangayId;
    }

    public int getUserId(){
        return this.userId;
    }
}
