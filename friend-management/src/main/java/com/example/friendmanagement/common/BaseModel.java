package com.example.friendmanagement.common;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
public abstract class BaseModel {
    @Id
    @Column("id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
}
