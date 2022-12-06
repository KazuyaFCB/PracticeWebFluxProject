package com.example.friendmanagement.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitTestException extends RuntimeException {
    private Throwable cause;

    public UnitTestException(Throwable cause) {
        this.cause = cause;
    }
}
