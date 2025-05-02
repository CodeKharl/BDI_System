package org.isu_std.io.dynamic_enum_handler;

public final class StringArrValue implements ConfigValue{
    private final String[] value;

    public StringArrValue(String[] value){
        this.value = value;
    }

    public String[] getValue(){
        return this.value;
    }

    public Object get(){
        return this.value;
    }
}
