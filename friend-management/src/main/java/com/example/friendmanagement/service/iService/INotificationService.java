package com.example.friendmanagement.service.iService;

import com.example.friendmanagement.api.request.GetAllNotifiedEmailsRequest;
import com.example.friendmanagement.api.response.GetAllNotifiedEmailsResponse;
import reactor.core.publisher.Mono;

public interface INotificationService {
    Mono<GetAllNotifiedEmailsResponse> getAllNotifiedEmails(GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest);
}
