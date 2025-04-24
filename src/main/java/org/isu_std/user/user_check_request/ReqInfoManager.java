package org.isu_std.user.user_check_request;

import org.isu_std.models.Document;

import java.util.List;
import java.util.Map;

public class ReqInfoManager {
    private final int barangayId;
    private final int userId;

    private Map<Integer, Document> documentDetailMap;

    public ReqInfoManager(int barangayId, int userId){
        this.barangayId = barangayId;
        this.userId = userId;
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
