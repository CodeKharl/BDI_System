package org.isu_std.io.folder_setup;

import java.io.File;

public enum FolderConfig {
    DOC_DOCUMENT_DIRECTORY(".bdis\\data\\brgy_docs"),
    DOC_REQUEST_DIRECTORY(".bdis\\data\\doc_request"),
    DOC_APPROVE_DIRECTORY(".bdis\\data\\approved_request");

    private final String directory;

    FolderConfig(String directory){
        this.directory = directory;
    }

    public String getDirectory(){
        return System.getProperty("user.home") + File.separator + directory;
    }
}
