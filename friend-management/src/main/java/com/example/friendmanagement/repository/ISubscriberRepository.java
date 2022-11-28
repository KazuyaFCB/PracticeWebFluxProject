package com.example.friendmanagement.repository;

import com.example.friendmanagement.model.SubscriberEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ISubscriberRepository extends R2dbcRepository<SubscriberEntity, Long> {
    @Query("SELECT requestor FROM friend_management.subscriber WHERE target = :target")
    Flux<String> findRequestorByTarget(String target);
}
