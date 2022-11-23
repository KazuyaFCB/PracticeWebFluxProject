package com.example.friendmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Column;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }
}
