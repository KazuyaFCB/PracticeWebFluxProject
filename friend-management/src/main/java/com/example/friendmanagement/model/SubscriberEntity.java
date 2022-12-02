package com.example.friendmanagement.model;

import com.example.friendmanagement.common.BaseModel;
import com.example.friendmanagement.util.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Constant.SUBSCRIBER, schema = Constant.FRIEND_MANAGEMENT)
public class SubscriberEntity extends BaseModel {
    @Column("requestor")
    private String requestor;

    @Column("target")
    private String target;
}
