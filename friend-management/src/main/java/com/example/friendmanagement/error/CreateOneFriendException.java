package com.example.friendmanagement.error;

public class CreateOneFriendException extends RuntimeException {
    private Throwable throwable;

    public CreateOneFriendException(Throwable throwable) {
        this.throwable = throwable;
    }
}
