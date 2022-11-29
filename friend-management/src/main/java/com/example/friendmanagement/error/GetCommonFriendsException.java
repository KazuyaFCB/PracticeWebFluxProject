package com.example.friendmanagement.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommonFriendsException extends RuntimeException {
    private Throwable cause;

    public GetCommonFriendsException(Throwable cause) {
        this.cause = cause;
    }
}
