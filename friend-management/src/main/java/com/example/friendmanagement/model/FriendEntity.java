package com.example.friendmanagement.model;

import com.example.friendmanagement.common.BaseModel;
import com.example.friendmanagement.util.Constant;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.Entity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Constant.FRIEND, schema = Constant.FRIEND_MANAGEMENT)
public class FriendEntity extends BaseModel {
    @Column("email1")
    private String email1;

    @Column("email2")
    private String email2;
}
