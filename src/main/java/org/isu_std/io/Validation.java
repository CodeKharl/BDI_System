package org.isu_std.io;

import java.util.Arrays;

public final class Validation {
    private enum Regex{
        LETTERS_ONLY("^[a-zA-Z]+$"),
        LETTERS_WITH_SPACE_ONLY("^[a-zA-Z]+( [a-zA-Z]+)*$"),
        NUMBERS_ONLY("[0-9]\\d*");

        private final String regexType;
        Regex(String regexType){
            this.regexType = regexType;
        }

        private String getRegexType(){
            return this.regexType;
        }
    }

    public static boolean isInputLengthAccepted(int validLength, String type, String input){
        if(input.length() < validLength){
            Util.printMessage(
                    "The %s you input is too short! Please try again.".formatted(type)
            );

            return false;
        }

        return true;
    }

    public static boolean isInputLengthAccepted(int validLength, String input){
        return input.length() >= validLength;
    }

    public static boolean isInputMatches(String input, String matchesStr, String matchesType){
        if(!input.matches(matchesStr)){
            Util.printMessage(
                    "%s are only allowed to Input! Please try again.".formatted(matchesType)
            )
            ;
            return false;
        }

        return true;
    }


    public static boolean isInputMatches(String input, String matchesStr){
        return input.matches(matchesStr);
    }

    public static boolean isInputMatchesNumbers(String input){
        return isInputMatches(
                input,
                Regex.NUMBERS_ONLY.regexType
        );
    }

    public static boolean isInputMatchesLettersWithSpaces(String input){
        return isInputMatches(
                input,
                Regex.LETTERS_WITH_SPACE_ONLY.regexType
        );
    }

    public static boolean isInputMatchesNumbers(String input, String type){
        return isInputMatches(
                input,
                Regex.NUMBERS_ONLY.regexType,
                type
        );
    }

    public static boolean isInputMatchesLetters(String input){
        return isInputMatches(input, Regex.LETTERS_ONLY.regexType);
    }

    private static boolean isInputLength_MatchesAccepted(int validLength, String matchesStr, String matchesType, String type, String input){
        if(!isInputMatches(input, matchesStr, matchesType)){
            return false;
        }

        return isInputLengthAccepted(validLength, type, input);
    }

    public static boolean isInputLength_MatchesNumbers(int validLength, String type, String input){
        return isInputLength_MatchesAccepted(
                validLength,
                Regex.NUMBERS_ONLY.regexType,
                "Numbers",
                type,
                input
        );
    }

    public static boolean isInputLength_MatchesLetters(int validLength, String type, String input){
        return isInputLength_MatchesAccepted(
                validLength,
                Regex.LETTERS_ONLY.regexType,
                "Letters",
                type,
                input
        );
    }

    public static <T> boolean isObjectExists(T type, String addMessage){
        if (type == null) {
            Util.printMessage("%s not exists! Please try again.".formatted(addMessage));
            return false;
        }
        return true;
    }

    public static <T> boolean ObjectEquals(T type, T matches, String addMessage){
        if (type.equals(matches)) {
            return true;
        }

        Util.printMessage("Wrong %s! Please try again.".formatted(addMessage));
        return false;
    }
}
