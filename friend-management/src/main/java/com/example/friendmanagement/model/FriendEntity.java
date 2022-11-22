package com.example.friendmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Table(name = "friend", schema = "friend_management")
@Entity
public class FriendEntity {
    public FriendEntity(Long id, String email1, String email2) {
        this.id = id;
        this.email1 = email1;
        this.email2 = email2;
    }

    @Id
    @JsonIgnore
    private Long id;
    private String email1;
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
