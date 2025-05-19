package org.isu_std.io.custom_exception;

public class DataAccessException extends RuntimeException{
    public DataAccessException(String message, Throwable cause){
        super(message, cause);
    }
}
