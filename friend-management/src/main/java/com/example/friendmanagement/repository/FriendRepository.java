package com.example.friendmanagement.repository;

import com.example.friendmanagement.model.FriendEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface FriendRepository extends ReactiveCrudRepository<FriendEntity, String> {

}
