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
import com.example.friendmanagement.util.Constant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping(value = Constant.API_FRIEND)
public class FriendController implements IFriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/get-all")
    private Flux<FriendEntity> getAllFriends() {
        return friendService.getAll();
    }

    @PostMapping(Constant.CREATE_ONE)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Mono<CreateOneFriendResponse> createOneFriend(@RequestBody @NonNull CreateOneFriendRequest createOneFriendRequest) {
        return friendService.createOneFriend(createOneFriendRequest);
    }

    @PostMapping(Constant.GET_ALL_BY_EMAIL)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Mono<GetAllFriendsResponse> getAllFriendsByEmail(@RequestBody @NonNull GetAllFriendsRequest getAllFriendsRequest) {
        return friendService.getAllFriendsByEmail(getAllFriendsRequest);
    }

    @PostMapping(Constant.GET_COMMON_FRIENDS)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Mono<GetCommonFriendsResponse> getCommonFriends(@RequestBody @NonNull GetCommonFriendsRequest getCommonFriendsRequest) {
        return friendService.getCommonFriends(getCommonFriendsRequest);
    }
}
