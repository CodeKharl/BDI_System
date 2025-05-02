package org.isu_std.io.dynamic_enum_handler;

public final class IntValue implements ConfigValue{
    private final int value;

    public IntValue(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public Object get() {
        return value;
    }
}
