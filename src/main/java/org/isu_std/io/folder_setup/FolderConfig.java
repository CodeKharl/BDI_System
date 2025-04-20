package org.isu_std.io.folder_setup;

import java.io.File;

public enum FolderConfig {
    DOC_DOCUMENT_PATH(".bdis\\data\\brgy_docs"),
    DOC_REQUEST_PATH(".bdis\\data\\doc_request"),
    DOC_APPROVE_PATH(".bdis\\data\\approved_request");

    private final String path;

    FolderConfig(String path){
        this.path = path;
    }

    public String getPath(){
        return System.getProperty("user.home") + File.separator + path;
    }
}
