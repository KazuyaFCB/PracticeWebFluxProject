package com.example.friendmanagement.model;

import com.example.friendmanagement.common.BaseModel;
import com.example.friendmanagement.util.Constant;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Constant.BLOCKER, schema = Constant.FRIEND_MANAGEMENT)
public class BlockerEntity extends BaseModel {
    @Column("requestor")
    private String requestor;

    @Column("target")
    private String target;
}
