package com.example.friendmanagement.controller.iController;

import com.example.friendmanagement.api.request.GetAllNotifiedEmailsRequest;
import com.example.friendmanagement.api.response.GetAllNotifiedEmailsResponse;
import reactor.core.publisher.Mono;

public interface INotificationController {
    Mono<GetAllNotifiedEmailsResponse> getAllNotifiedEmails(GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest);
}
