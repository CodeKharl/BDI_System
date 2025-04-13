package org.isu_std.io.folder_setup;

public enum FolderConfig {
    DOC_REQUEST_PATH("\\.bdis\\data\\docRequest");

    private final String path;

    FolderConfig(String path){
        this.path = path;
    }

    public String getPath(){
        return System.getProperty("user.home") + path;
    }
}
