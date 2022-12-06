package com.example.friendmanagement.service.serviceImpl;

import com.example.friendmanagement.api.request.CreateOneBlockerRequest;
import com.example.friendmanagement.api.response.CreateOneBlockerResponse;
import com.example.friendmanagement.error.CreateOneBlockerException;
import com.example.friendmanagement.error.CreateOneFriendException;
import com.example.friendmanagement.model.BlockerEntity;
import com.example.friendmanagement.repository.IBlockerRepository;
import com.example.friendmanagement.service.iService.IBlockerService;
import com.example.friendmanagement.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class BlockerServiceImpl implements IBlockerService {
    @Autowired
    private IBlockerRepository iBlockerRepository;

    @Override
    public Mono<CreateOneBlockerResponse> createOneBlocker(CreateOneBlockerRequest createOneBlockerRequest) {
        return Mono.justOrEmpty(createOneBlockerRequest)
                .filter(request -> Objects.nonNull(request) && Objects.nonNull(request.getRequestor()) && Objects.nonNull(request.getTarget()))
                .switchIfEmpty(Mono.error(new CreateOneFriendException(new Throwable(Constant.REQUEST_BODY_IS_INVALID))))
                .flatMap(request -> iBlockerRepository.save(BlockerEntity.builder().requestor(request.getRequestor()).target(request.getTarget()).build()))
                .map(createdBlockerEntity -> CreateOneBlockerResponse.builder().success(true).build())
                .onErrorMap(e -> new CreateOneBlockerException(e.getCause()));
    }
}
