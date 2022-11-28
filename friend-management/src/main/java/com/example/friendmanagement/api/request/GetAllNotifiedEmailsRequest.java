package com.example.friendmanagement.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllNotifiedEmailsRequest {
    private String sender;
    private String text;
}
