package com.example.friendmanagement.error;

public class GetCommonFriendsException extends RuntimeException {
    private Throwable throwable;

    public GetCommonFriendsException(Throwable throwable) {
        this.throwable = throwable;
    }
}
