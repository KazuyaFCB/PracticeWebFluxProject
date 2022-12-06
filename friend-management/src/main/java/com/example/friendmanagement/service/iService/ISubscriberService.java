package com.example.friendmanagement.service.iService;

import com.example.friendmanagement.api.request.CreateOneSubscriberRequest;
import com.example.friendmanagement.api.response.CreateOneSubscriberResponse;
import reactor.core.publisher.Mono;

public interface ISubscriberService {
    Mono<CreateOneSubscriberResponse> createOneSubscriber(CreateOneSubscriberRequest createOneSubscriberRequest);
}
