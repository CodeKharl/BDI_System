package org.isu_std.io.custom_exception;

public class OperationFailedException extends RuntimeException{
    public OperationFailedException(String message) {
        super(message);
    }
    public OperationFailedException(String message, Throwable cause){
        super(message, cause);
    }
}
