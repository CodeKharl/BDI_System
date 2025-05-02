package org.isu_std.io.dynamic_enum_handler;

public sealed interface ConfigValue permits CharValue, StringValue, IntValue, StringArrValue{
    Object get();
}
