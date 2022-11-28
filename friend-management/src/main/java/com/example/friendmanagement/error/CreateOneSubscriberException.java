package com.example.friendmanagement.error;

public class CreateOneSubscriberException extends RuntimeException {

    private Throwable throwable;

    public CreateOneSubscriberException(Throwable throwable) {
        this.throwable = throwable;
    }
}
