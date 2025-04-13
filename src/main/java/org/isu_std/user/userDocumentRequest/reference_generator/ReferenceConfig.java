package org.isu_std.user.userDocumentRequest.reference_generator;

public enum ReferenceConfig {
    REQUEST_PREFIX("REQ"),
    TIME_FORMAT("HHmmss"),
    DEFAULT_RANDOM_LENGTH(7),
    ALPHABET("ABCDEFGHIJKLMNOPQRSTUVWXYNZ"),
    NUMBERS("0123456789");;

    private final String strValue;
    private final int intValue;

    ReferenceConfig(String value){
        this.strValue = value;
        this.intValue = 0;
    }

    ReferenceConfig(int value){
        this.intValue = value;
        this.strValue = null;
    }

    public Object getValue(){
        return strValue != null ? strValue : intValue;
    }
}
