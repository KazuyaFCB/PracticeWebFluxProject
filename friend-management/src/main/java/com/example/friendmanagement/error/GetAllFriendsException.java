package com.example.friendmanagement.error;

import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class GetAllFriendsException extends Exception {
    public GetAllFriendsException() {
    }
}
