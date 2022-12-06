package com.example.friendmanagement.service.iService;

import com.example.friendmanagement.api.request.CreateOneBlockerRequest;
import com.example.friendmanagement.api.response.CreateOneBlockerResponse;
import reactor.core.publisher.Mono;

public interface IBlockerService {
    Mono<CreateOneBlockerResponse> createOneBlocker(CreateOneBlockerRequest createOneBlockerRequest);
}
