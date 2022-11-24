package com.example.friendmanagement.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommonFriendsResponse {
    private boolean success;
    private List<String> friends;
    private int count;
}
