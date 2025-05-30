package org.isu_std.models;

public class ModelHelper {
    public static Object getFirstExistingValue(Object[] infoValues){
        for (Object value : infoValues){
            if(isDocValueExist(value)){
                return value;
            }
        }

        throw new NullPointerException("Array object has no existing values.");
    }

    private static boolean isDocValueExist(Object value){
        return switch (value){
            case null -> false;
            case Character charVal -> charVal != '\u0000';
            case Integer intVal -> intVal > 0;
            case Double dobValue -> dobValue > 0;
            default -> !value.toString().isBlank();
        };
    }
}
