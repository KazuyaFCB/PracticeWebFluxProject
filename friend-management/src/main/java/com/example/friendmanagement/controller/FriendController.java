package com.example.friendmanagement.controller;

import com.example.friendmanagement.model.FriendEntity;
import com.example.friendmanagement.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/get-all")
    private Flux<FriendEntity> readAllFriends() {
        return friendService.readAll();
    }

//    @PostMapping("/create-one")
//    private Mono<FriendEntity> createOneFriends(@RequestBody FriendEntity request) {
//        return friendService.createOne(request);
//    }
}
