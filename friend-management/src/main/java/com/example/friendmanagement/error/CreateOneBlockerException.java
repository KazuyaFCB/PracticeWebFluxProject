package com.example.friendmanagement.error;

public class CreateOneBlockerException extends RuntimeException {

    private Throwable throwable;

    public CreateOneBlockerException(Throwable throwable) {
        this.throwable = throwable;
    }
}
