package com.example.friendmanagement.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOneBlockerException extends RuntimeException {

    private Throwable cause;

    public CreateOneBlockerException(Throwable cause) {
        this.cause = cause;
    }
}
