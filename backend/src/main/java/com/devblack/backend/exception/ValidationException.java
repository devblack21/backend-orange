package com.devblack.backend.exception;

public class ValidationException extends Exception{

    private static final long serialVersionUID = 1476542066938633226L;

    public ValidationException (String message) {
        super(message);
    }

}
