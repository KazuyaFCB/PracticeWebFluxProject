package com.example.friendmanagement.service;

import com.example.friendmanagement.model.FriendEntity;
//import com.example.friendmanagement.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FriendService {
//    @Autowired
//    private FriendRepository friendRepository;

    public Flux<FriendEntity> readAll() {
        return Flux.just(new FriendEntity(1L, "u1@example.com","u2@example.com") , new FriendEntity(2L, "u3@example.com", "u1@example.com"));
    }

//    public Mono<FriendEntity> createOne(FriendEntity friendEntity) {
//        return friendRepository.save(friendEntity);
//    }
}
