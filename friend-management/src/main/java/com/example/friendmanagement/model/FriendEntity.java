package com.example.friendmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Column;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friend", schema = "friend_management")
public class FriendEntity {

    public FriendEntity(String email1, String email2) {
        this.email1 = email1;
        this.email2 = email2;
    }

    @Id
    @Column("id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column("email1")
    private String email1;
    @Column("email2")
    private String email2;

}
