package org.isu_std.io.collections_enum;

public enum ChoiceCollection {
    CONFIRM('C'),
    SUB_CANCEL('D'),
    OPEN('O'),
    EXIT_CODE('$'),
    EXIT_INT_CODE(0);

    private char value;
    private int intValue;

    ChoiceCollection(char value){
        this.value = value;
    }
    ChoiceCollection(int intValue){
        this.intValue = intValue;
    }

    public char getValue(){
        return this.value;
    }

    public int getIntValue(){
        return this.intValue;
    }
}
