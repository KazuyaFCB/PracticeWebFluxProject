package com.example.friendmanagement.repository;

import com.example.friendmanagement.model.BlockerEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBlockerRepository extends R2dbcRepository<BlockerEntity, Long> {
    @Query("SELECT COUNT(1) FROM friend_management.blocker WHERE (requestor = :email1 AND target = :email2) OR (requestor = :email2 AND target = :email1) ")
    Mono<Integer> countEmailBlockEachOther(String email1, String email2);

    @Query("SELECT requestor FROM friend_management.blocker WHERE target = :target")
    Flux<String> findRequestorByTarget(String target);

    @Query("SELECT target FROM friend_management.blocker WHERE requestor = :requestor")
    Flux<String> findTargetByRequestor(String requestor);
}
