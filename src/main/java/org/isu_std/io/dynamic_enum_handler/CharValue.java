package org.isu_std.io.dynamic_enum_handler;

public final class CharValue implements ConfigValue{
    private final char value;

    public CharValue(char value){
        this.value = value;
    }

    public char getValue(){
        return this.value;
    }

    @Override
    public Object get() {
        return value;
    }
}
