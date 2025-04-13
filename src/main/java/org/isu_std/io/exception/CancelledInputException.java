package org.isu_std.io.exception;

import org.isu_std.io.Symbols;

public class CancelledInputException extends Exception{
    public CancelledInputException(String message){
        super(Symbols.INPUT_CANCELLED.getType() + message);
    }
}
