package com.example.friendmanagement.controller.iController;

import com.example.friendmanagement.api.request.CreateOneSubscriberRequest;
import com.example.friendmanagement.api.response.CreateOneSubscriberResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface ISubscriberController {
    Mono<CreateOneSubscriberResponse> createOneSubscriber(@RequestBody @NonNull CreateOneSubscriberRequest createOneSubscriberRequest);
}
