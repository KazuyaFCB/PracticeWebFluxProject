package com.example.friendmanagement.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllFriendsException extends RuntimeException {
    private Throwable cause;

    public GetAllFriendsException(Throwable cause) {
        this.cause = cause;
    }
}
