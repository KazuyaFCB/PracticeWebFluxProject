package com.example.friendmanagement.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOneFriendException extends RuntimeException {
    private Throwable cause;

    public CreateOneFriendException(Throwable cause) {
        this.cause = cause;
    }
}
