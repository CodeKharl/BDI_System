package org.isu_std.io;

// Use for better understanding the system input and output processes.

public enum Symbols {
    // Different sign used for printing an output type.

    SECTION_TITLE(">>> "),
    CHOICES("--- "),
    QUESTION("*** "),
    MESSAGE("___ "),
    INPUT(QUESTION.type + "--> "),
    SECTION_START("~~~~()"),
    INFORMATION("]]] "),

    INPUT_CANCELLED("!!! "),
    EXCEPTION("### ");

    private final String type;

    Symbols(String type){
        this.type = type;
    }

    public final String getType(){
        return this.type;
    }
}
