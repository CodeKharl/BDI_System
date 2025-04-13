package org.isu_std.io;

public class SystemUnit {
    private final static String SYSTEM_IN_SYMBOL = "=== ";

    public static <O> boolean isObjectSets(O object, String objectName ){
        if(object == null){
            System.out.printf("%s%s has no injected dependency.", SYSTEM_IN_SYMBOL, objectName);
            return false;
        }

        return true;
    }

    public static void printSystemUnitMessage(String message){
        System.out.println(SYSTEM_IN_SYMBOL + message);
    }
}
