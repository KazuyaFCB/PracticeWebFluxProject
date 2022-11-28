package com.example.friendmanagement.error;

public class GetAllFriendsException extends RuntimeException {
    private Throwable throwable;

    public GetAllFriendsException(Throwable throwable) {
        this.throwable = throwable;
    }
}
