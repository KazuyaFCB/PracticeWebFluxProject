package com.example.friendmanagement.service.serviceImpl;

import com.example.friendmanagement.api.request.CreateOneSubscriberRequest;
import com.example.friendmanagement.api.response.CreateOneSubscriberResponse;
import com.example.friendmanagement.error.CreateOneSubscriberException;
import com.example.friendmanagement.model.SubscriberEntity;
import com.example.friendmanagement.repository.ISubscriberRepository;
import com.example.friendmanagement.service.iService.ISubscriberService;
import com.example.friendmanagement.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class SubscriberServiceImpl implements ISubscriberService {
    @Autowired
    private ISubscriberRepository iSubscriberRepository;

    @Override
    public Mono<CreateOneSubscriberResponse> createOneSubscriber(CreateOneSubscriberRequest createOneSubscriberRequest) {
        return Mono.justOrEmpty(createOneSubscriberRequest)
                .filter(request -> Objects.nonNull(request) && Objects.nonNull(request.getRequestor()) && Objects.nonNull(request.getTarget()))
                .switchIfEmpty(Mono.error(new CreateOneSubscriberException(new Throwable(Constant.REQUEST_BODY_IS_INVALID))))
                .flatMap(request -> iSubscriberRepository.save(SubscriberEntity.builder().requestor(request.getRequestor()).target(request.getTarget()).build()))
                .map(createdSubscriberEntity -> CreateOneSubscriberResponse.builder().success(true).build())
                .onErrorMap(e -> new CreateOneSubscriberException(e.getCause()));
    }
}
