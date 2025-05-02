package org.isu_std.io.dynamic_enum_handler;

public enum EnumExceptionMessage{
    STRING("String"),
    INT("Integer"),
    CHAR("Character"),
    STRING_ARRAY("Array String");

    private final String value;

    EnumExceptionMessage(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public String getExpectedMessage(){
        return "Expected a %s, but got : %s"
                .formatted(value, value.getClass().getSimpleName());
    }
}
