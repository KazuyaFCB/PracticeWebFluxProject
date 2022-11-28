package com.example.friendmanagement.error;

public class GetAllNotifiedEmailsException extends RuntimeException {
    private Throwable throwable;

    public GetAllNotifiedEmailsException(Throwable throwable) {
        this.throwable = throwable;
    }
}
