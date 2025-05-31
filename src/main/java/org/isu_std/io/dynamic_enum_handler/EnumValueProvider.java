package org.isu_std.io.dynamic_enum_handler;

public class EnumValueProvider {
    public static char getCharValue(ConfigValue value){
        if(value instanceof CharValue charValue){
            return charValue.value();
        }

        throw new IllegalStateException(EnumExceptionMessage.CHAR.getExpectedMessage());
    }

    public static String[] getStringArrValue(ConfigValue value){
        if(value instanceof StringArrValue stringArrValue){
            return stringArrValue.value();
        }

        throw new IllegalStateException(EnumExceptionMessage.STRING_ARRAY.getExpectedMessage());
    }

    public static int getIntValue(ConfigValue configValue){
        if(configValue instanceof IntValue intValue){
            return intValue.value();
        }

        throw new IllegalStateException(EnumExceptionMessage.INT.getExpectedMessage());
    }

    public static String getStringValue(ConfigValue configValue){
        if(configValue instanceof StringValue stringValue){
            return stringValue.value();
        }

        throw new IllegalStateException(EnumExceptionMessage.STRING.getExpectedMessage());
    }
}
