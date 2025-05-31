package org.isu_std.io.dynamic_enum_handler;

public record StringArrValue(String[] value) implements ConfigValue {
    @Override
    public Object get() {
        return this.value;
    }
}
