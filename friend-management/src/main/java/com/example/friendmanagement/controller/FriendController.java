package com.example.friendmanagement.controller;

import com.example.friendmanagement.api.request.CreateOneFriendRequest;
import com.example.friendmanagement.api.request.GetAllFriendsRequest;
import com.example.friendmanagement.api.request.GetCommonFriendsRequest;
import com.example.friendmanagement.api.response.CreateOneFriendResponse;
import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import com.example.friendmanagement.api.response.GetCommonFriendsResponse;
import com.example.friendmanagement.error.GetAllFriendsException;
import com.example.friendmanagement.model.FriendEntity;
import com.example.friendmanagement.service.FriendService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping(value = "/api/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/get-all")
    private Flux<FriendEntity> getAllFriends() {
        return friendService.getAll();
    }

    @PostMapping("/create-one")
    private Mono<CreateOneFriendResponse> createOneFriend(@RequestBody @NonNull CreateOneFriendRequest createOneFriendRequest) {
        return friendService.createOneFriend(createOneFriendRequest);
    }

    @PostMapping("/get-all-by-email")
    private Mono<GetAllFriendsResponse> getAllFriendsByEmail(@RequestBody @NonNull GetAllFriendsRequest getAllFriendsRequest) throws Exception {
        return friendService.getAllFriendsByEmail(getAllFriendsRequest);
    }

    @PostMapping("/get-common-friends")
    private Mono<GetCommonFriendsResponse> getCommonFriends(@RequestBody @NonNull GetCommonFriendsRequest getCommonFriendsRequest) {
        return friendService.getCommonFriends(getCommonFriendsRequest);
    }
}
