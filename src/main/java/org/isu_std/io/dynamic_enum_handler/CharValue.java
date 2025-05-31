package org.isu_std.io.dynamic_enum_handler;

public record CharValue(char value) implements ConfigValue {
    @Override
    public Object get() {
        return value;
    }
}
