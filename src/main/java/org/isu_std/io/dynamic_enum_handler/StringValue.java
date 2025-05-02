package org.isu_std.io.dynamic_enum_handler;

public final class StringValue implements ConfigValue{
    private final String value;

    public StringValue(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public Object get(){
        return value;
    }
}
