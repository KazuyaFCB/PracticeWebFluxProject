package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.CreateOneFriendRequest;
import com.example.friendmanagement.api.request.GetAllFriendsRequest;
import com.example.friendmanagement.api.request.GetCommonFriendsRequest;
import com.example.friendmanagement.api.response.CreateOneFriendResponse;
import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import com.example.friendmanagement.api.response.GetCommonFriendsResponse;
import com.example.friendmanagement.controller.iController.IFriendController;
import com.example.friendmanagement.service.serviceImpl.FriendServiceImpl;
import com.example.friendmanagement.util.Constant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping(value = Constant.API_FRIEND)
public class FriendControllerImpl implements IFriendController {
    @Autowired
    private FriendServiceImpl friendServiceImpl;

    @PostMapping(Constant.CREATE_ONE)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Mono<CreateOneFriendResponse> createOneFriend(@RequestBody @NonNull CreateOneFriendRequest createOneFriendRequest) {
        return friendServiceImpl.createOneFriend(createOneFriendRequest);
    }

    @PostMapping(Constant.GET_ALL_BY_EMAIL)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Mono<GetAllFriendsResponse> getAllFriendsByEmail(@RequestBody @NonNull GetAllFriendsRequest getAllFriendsRequest) {
        return friendServiceImpl.getAllFriendsByEmail(getAllFriendsRequest);
    }

    @PostMapping(Constant.GET_COMMON_FRIENDS)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Mono<GetCommonFriendsResponse> getCommonFriends(@RequestBody @NonNull GetCommonFriendsRequest getCommonFriendsRequest) {
        return friendServiceImpl.getCommonFriends(getCommonFriendsRequest);
    }
}
