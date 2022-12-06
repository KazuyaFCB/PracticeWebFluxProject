package com.example.friendmanagement.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOneSubscriberException extends RuntimeException {

    private Throwable cause;

    public CreateOneSubscriberException(Throwable cause) {
        this.cause = cause;
    }
}
