package com.example.friendmanagement.service;

import com.example.friendmanagement.model.FriendEntity;
import com.example.friendmanagement.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    public Flux<FriendEntity> getAll() {
        return Flux.just(new FriendEntity("u1@example.com","u2@example.com") , new FriendEntity("u3@example.com", "u1@example.com"));
    }

    public Mono<FriendEntity> createOne(FriendEntity friendEntity) {
        return friendRepository.save(friendEntity);
    }

    public Flux<String> getAllByEmail(String email) {
        Flux<String> email1Entities = friendRepository.findEmail1ByEmail2(email);
        Flux<String> email2Entities = friendRepository.findEmail2ByEmail1(email);
        return email1Entities.mergeWith(email2Entities);
    }

    public Flux<String> getCommon(String email1, String email2) {
        return friendRepository.findCommonEmail(email1, email2);
    }
}
