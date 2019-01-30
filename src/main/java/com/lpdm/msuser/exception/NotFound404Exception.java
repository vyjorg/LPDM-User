package com.lpdm.msuser.exception;

public class NotFound404Exception extends RuntimeException {

    public NotFound404Exception(){
        super("The requested resource could not be found.");
    }
}
