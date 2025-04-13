package org.isu_std.io.file_setup;

public enum MSWordMessage {
    NEED_DOCX_FILE_MESSAGE("File must be a type of docx file."),
    NOT_DOCX_FILE_MESSAGE("The file is not a docx file!"),
    NEED_CONTAIN_TEXT_PLACEHOLDERS("File must contain text place holders."),
    NOT_CONTAIN_TEXT_PLACEHOLDERS("The file doesn't contain text place holders");

    private final String value;

    MSWordMessage(String value){
        this.value = value;
    }

    public String getMessage(){
        return this.value;
    }
}
