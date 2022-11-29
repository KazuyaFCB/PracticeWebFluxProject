package com.example.friendmanagement.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllNotifiedEmailsException extends RuntimeException {
    private Throwable cause;

    public GetAllNotifiedEmailsException(Throwable cause) {
        this.cause = cause;
    }
}
