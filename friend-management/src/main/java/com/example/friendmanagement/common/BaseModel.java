package com.example.friendmanagement.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
public abstract class BaseModel {
    @Id
    @Column("id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
}
