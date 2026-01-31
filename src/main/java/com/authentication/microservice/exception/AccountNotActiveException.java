package com.authentication.microservice.exception;

public class AccountNotActiveException extends RuntimeException {

    public AccountNotActiveException() {
        super();
    }

    public AccountNotActiveException(String message) {
        super(message);
    }

     public AccountNotActiveException(String message, Throwable cause) {
        super(message);
    }

}
