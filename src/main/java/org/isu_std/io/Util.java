package org.isu_std.io;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class Util{
    public static void printInputInvalidMessage(String errorType){
        // Used for messaging an input that is not valid
        System.out.printf("%sInvalid %s! Please enter again.\n", Symbols.MESSAGE.getType(), errorType);
    }

    public static void printOnlyNumberMessage(){
        // Mainly used in NumberFormatException.
        System.out.printf("%sNumber input is only applicable in this section.\n", Symbols.MESSAGE.getType());
    }

    public static void printQuestion(String str){
        System.out.printf("%s%s", Symbols.QUESTION.getType(), str);
    }

    public static void printMessage(String str){
        System.out.printf("%s%s\n", Symbols.MESSAGE.getType(), str);
    }

    public static void printChoices(String str){
        System.out.printf("%s%s\n", Symbols.CHOICES.getType(), str);
    }

    public static void printInformation(String str){
        System.out.printf("%s%s\n", Symbols.INFORMATION.getType(), str);
    }

    public static <T> void printInformation(T type){
        System.out.println(Symbols.INFORMATION.getType() + type.toString());
    }

    public static void printException(String message){
        System.out.println(Symbols.EXCEPTION.getType() + message);
    }

    public static void printSectionTitle(String title){
        System.out.printf("%s\n%s%s\n",
                Symbols.SECTION_START.getType(),
                Symbols.SECTION_TITLE.getType(), title
        );
    }

    public static void printSubSectionTitle(String subTitle){
        System.out.printf("%s%s\n", Symbols.SECTION_TITLE.getType(), subTitle);
    }

    public static void printDefaultSectionLine(){
        System.out.println(Symbols.SECTION_START.getType());
    }

    // For printing any string with a symbol.
    public static void println(@NotNull Symbols symbols, String str){
        System.out.printf("%s%s\n", symbols.getType(), str);
    }
    public static void print(@NotNull Symbols symbols, String str){
        System.out.printf("%s%s", symbols.getType(), str);
    }

    public static void printChoices(String[] contents){
        for(int i = 0; i < contents.length; i++){
            Util.printChoices("%d. %s".formatted(i + 1, contents[i]));
        }
    }

    public static <T> void printInformation(String format, T[] types, String[] labels, String title){
        Util.printSectionTitle(title);

        for (int i = 0; i < types.length; i++) {
            printInformation(format.formatted(labels[i], types[i].toString()));
        }
    }
}

