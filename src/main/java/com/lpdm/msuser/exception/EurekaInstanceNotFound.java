package com.lpdm.msuser.exception;

public class EurekaInstanceNotFound extends RuntimeException {

    public EurekaInstanceNotFound(){
        super("Instance not found !");
    }
}
