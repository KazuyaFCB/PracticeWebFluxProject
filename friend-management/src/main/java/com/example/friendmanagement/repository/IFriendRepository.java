package com.example.friendmanagement.repository;

import com.example.friendmanagement.model.FriendEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;


public interface IFriendRepository extends R2dbcRepository<FriendEntity, Long> {
    @Query("SELECT email2 FROM friend_management.friend WHERE email1 = :email1")
    Flux<String> findEmail2ByEmail1(String email1);

    @Query("SELECT email1 FROM friend_management.friend WHERE email2 = :email2")
    Flux<String> findEmail1ByEmail2(String email2);

    @Query( "SELECT email\n" +
            "FROM ( \n" +
            "\tSELECT email1 as email FROM friend_management.friend WHERE email2 IN (:email1, :email2) AND email1 NOT IN (:email1, :email2)\n" +
            "\tUNION ALL\n" +
            "\tSELECT email2 as email FROM friend_management.friend WHERE email1 IN (:email1, :email2) AND email2 NOT IN (:email1, :email2)\n" +
            ") t\n" +
            "GROUP BY email\n" +
            "HAVING COUNT(email) > 1")
    Flux<String> findCommonEmail(String email1, String email2);

}