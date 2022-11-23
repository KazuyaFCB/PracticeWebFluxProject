package com.example.friendmanagement.controller;

import com.example.friendmanagement.api.request.CreateOneFriendRequest;
import com.example.friendmanagement.api.request.GetAllFriendsRequest;
import com.example.friendmanagement.api.request.GetCommonFriendsRequest;
import com.example.friendmanagement.api.response.CreateOneFriendResponse;
import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import com.example.friendmanagement.api.response.GetCommonFriendsResponse;
import com.example.friendmanagement.model.FriendEntity;
import com.example.friendmanagement.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/get-all")
    private Flux<FriendEntity> getAllFriends() {
        return friendService.getAll();
    }

    @PostMapping("/create-one")
    private Mono<CreateOneFriendResponse> createOneFriend(@RequestBody CreateOneFriendRequest createOneFriendRequest) {
        CreateOneFriendResponse createOneFriendResponse = new CreateOneFriendResponse();
        try {
            if (createOneFriendRequest == null || createOneFriendRequest.getFriends() == null || createOneFriendRequest.getFriends().length > 2 || createOneFriendRequest.getFriends()[0].equals(createOneFriendRequest.getFriends()[1])) {
                createOneFriendResponse.setSuccess(false);
                return Mono.just(createOneFriendResponse);
            }
            String email1, email2;
            if (createOneFriendRequest.getFriends()[0].compareTo(createOneFriendRequest.getFriends()[1]) < 0) {
                email1 = createOneFriendRequest.getFriends()[0];
                email2 = createOneFriendRequest.getFriends()[1];
            } else {
                email1 = createOneFriendRequest.getFriends()[1];
                email2 = createOneFriendRequest.getFriends()[0];
            }
            Mono<FriendEntity> friendEntityAsMono = friendService.createOne(new FriendEntity(email1, email2));
            FriendEntity friendEntity = friendEntityAsMono.block();
            createOneFriendResponse.setSuccess(true);
        } catch (Exception e) {
            createOneFriendResponse.setSuccess(false);
        }
        return Mono.just(createOneFriendResponse);
    }

    @PostMapping("/get-all-by-email")
    private Flux<GetAllFriendsResponse> getAllFriendsByEmail(@RequestBody GetAllFriendsRequest getAllFriendsRequest) {
        GetAllFriendsResponse getAllFriendsResponse = new GetAllFriendsResponse();
        try {
            if (getAllFriendsRequest == null || getAllFriendsRequest.getEmail() == null) {
                getAllFriendsResponse.setFriends(null);
                getAllFriendsResponse.setCount(0);
                getAllFriendsResponse.setSuccess(false);
            } else {
                Flux<String> emailEntitiesAsFlux = friendService.getAllByEmail(getAllFriendsRequest.getEmail());
                List<String> emailEntities = emailEntitiesAsFlux.collectList().block();
                getAllFriendsResponse.setFriends(emailEntities);
                getAllFriendsResponse.setCount(emailEntities.size());
                getAllFriendsResponse.setSuccess(true);
            }
        } catch (Exception e) {
            getAllFriendsResponse.setFriends(null);
            getAllFriendsResponse.setCount(0);
            getAllFriendsResponse.setSuccess(false);
        }
        return Flux.just(getAllFriendsResponse);
    }

    @PostMapping("/get-common-friends")
    private Flux<GetCommonFriendsResponse> getCommonFriends(@RequestBody GetCommonFriendsRequest getCommonFriendsRequest) {
        GetCommonFriendsResponse getCommonFriendsResponse = new GetCommonFriendsResponse();
        try {
            if (getCommonFriendsRequest == null || getCommonFriendsRequest.getFriends() == null || getCommonFriendsRequest.getFriends().length > 2) {
                getCommonFriendsResponse.setFriends(null);
                getCommonFriendsResponse.setCount(0);
                getCommonFriendsResponse.setSuccess(false);
            } else {
                Flux<String> emailEntitiesAsFlux = friendService.getCommon(getCommonFriendsRequest.getFriends()[0], getCommonFriendsRequest.getFriends()[1]);
                List<String> emailEntities = emailEntitiesAsFlux.collectList().block();
                getCommonFriendsResponse.setFriends(emailEntities);
                getCommonFriendsResponse.setCount(emailEntities.size());
                getCommonFriendsResponse.setSuccess(true);
            }
        } catch (Exception e) {
            getCommonFriendsResponse.setFriends(null);
            getCommonFriendsResponse.setCount(0);
            getCommonFriendsResponse.setSuccess(false);
        }
        return Flux.just(getCommonFriendsResponse);
    }
}
