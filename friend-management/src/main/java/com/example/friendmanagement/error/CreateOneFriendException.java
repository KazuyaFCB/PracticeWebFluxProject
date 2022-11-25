package com.example.friendmanagement.error;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CreateOneFriendException extends Exception {
    public CreateOneFriendException(Throwable e) {
        log.error("can't create one, reason " + e.getCause());
    }
}
