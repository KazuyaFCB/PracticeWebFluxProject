package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.CreateOneSubscriberRequest;
import com.example.friendmanagement.api.response.CreateOneSubscriberResponse;
import com.example.friendmanagement.controller.iController.ISubscriberController;
import com.example.friendmanagement.service.serviceImpl.SubscriberServiceImpl;
import com.example.friendmanagement.util.Constant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping(value = Constant.API_SUBSCRIBER)
public class SubscriberControllerImpl implements ISubscriberController {
    @Autowired
    private SubscriberServiceImpl subscriberServiceImpl;

    @PostMapping(Constant.CREATE_ONE)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Mono<CreateOneSubscriberResponse> createOneSubscriber(@RequestBody @NonNull CreateOneSubscriberRequest createOneSubscriberRequest) {
        return subscriberServiceImpl.createOneSubscriber(createOneSubscriberRequest);
    }
}
