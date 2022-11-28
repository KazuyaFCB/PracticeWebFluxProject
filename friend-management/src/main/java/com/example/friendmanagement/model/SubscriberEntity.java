package com.example.friendmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriber", schema = "friend_management")
public class SubscriberEntity {
    @Id
    @Column("id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column("requestor")
    private String requestor;

    @Column("target")
    private String target;
}
