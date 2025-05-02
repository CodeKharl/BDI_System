package org.isu_std.io.collections;

public enum InputMessageCollection {
    INPUT_SHORT("The %s you input is too short! Please try again."),
    INPUT_OBJECT_NOT_EXIST("%s not exists! Please try again."),
    INPUT_NOT_EQUAL("Wrong %s! Please enter the correct pin."),
    INPUT_NUM_ONLY("Invalid %s! Numbers are only allowed to input."),
    INPUT_LETTERS_ONLY("Letters are only allowed to input! Please try again."),
    INPUT_INVALID("Invalid %s! Please enter again.");

    private final static String SPECIFIER_VALUE = "%s";
    private final String message;

    InputMessageCollection(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public String getFormattedMessage(String str){
        if(message.contains(SPECIFIER_VALUE)){
            return this.message.formatted(str);
        }

        throw new IllegalStateException(
                "Method invocation failed: no string specifier defined for the enum constant."
        );
    }
}
