package org.isu_std.user.user_document_request;

public enum DocRequestConfig {
    DOC_REQUIREMENT_PATH_DIVIDER("::"),

    CONFIRM_VALUE('R'), CANCEL_VALUE('X');

    private char cValue;
    private String value;

    DocRequestConfig(String value){
        this.value = value;
    }

    DocRequestConfig(char cValue){
        this.cValue = cValue;
    }

    public String getValue(){
        return this.value;
    }

    public char getCharValue(){
        return this.cValue;
    }

}
