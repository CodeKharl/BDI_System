package org.isu_std.io.custom_exception;

public class ServiceException extends RuntimeException{
    public ServiceException(String message){
        super(message);
    }
}
