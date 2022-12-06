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
public class GetAllNotifiedEmailsResponse {
    private boolean success;
    private List<String> recipients;
}
