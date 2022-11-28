package com.example.friendmanagement.controller.iController;

import com.example.friendmanagement.api.request.CreateOneBlockerRequest;
import com.example.friendmanagement.api.response.CreateOneBlockerResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface IBlockerController {
    Mono<CreateOneBlockerResponse> createOneBlocker(@RequestBody @NonNull CreateOneBlockerRequest createOneBlockerRequest);
}
