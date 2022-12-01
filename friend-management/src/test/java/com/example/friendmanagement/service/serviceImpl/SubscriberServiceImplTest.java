package com.example.friendmanagement.service.serviceImpl;

import com.example.friendmanagement.api.request.CreateOneSubscriberRequest;
import com.example.friendmanagement.api.response.CreateOneSubscriberResponse;
import com.example.friendmanagement.error.CreateOneSubscriberException;
import com.example.friendmanagement.model.SubscriberEntity;
import com.example.friendmanagement.repository.ISubscriberRepository;
import com.example.friendmanagement.service.iService.ISubscriberServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@Slf4j
public class SubscriberServiceImplTest implements ISubscriberServiceTest {
    @InjectMocks
    private SubscriberServiceImpl subscriberServiceImpl;

    @Mock
    private ISubscriberRepository iSubscriberRepository;

    @Test
    @Override
    public void testCreateOneSubscriber_Success() {
        CreateOneSubscriberRequest createOneSubscriberRequest = CreateOneSubscriberRequest.builder().requestor("u1").target("u2").build();
        CreateOneSubscriberResponse expectedCreateOneSubscriberResponse = CreateOneSubscriberResponse.builder().success(true).build();

        SubscriberEntity createdSubscriberEntity = SubscriberEntity.builder().requestor(createOneSubscriberRequest.getRequestor()).target(createOneSubscriberRequest.getTarget()).build();

        Mockito.when(iSubscriberRepository.save(createdSubscriberEntity))
                .thenReturn(Mono.just(createdSubscriberEntity));

        Mono<CreateOneSubscriberResponse> createOneSubscriberResponse = subscriberServiceImpl.createOneSubscriber(createOneSubscriberRequest);
        StepVerifier.create(createOneSubscriberResponse)
                .consumeNextWith(actualCreateOneSubscriberResponse -> Assertions.assertEquals(expectedCreateOneSubscriberResponse.isSuccess(), actualCreateOneSubscriberResponse.isSuccess()))
                .verifyComplete();
    }

    @Test
    @Override
    public void testCreateOneSubscriber_FailedWhenRequestIsNull() {
        CreateOneSubscriberRequest createOneSubscriberRequest = null;

        Mono<CreateOneSubscriberResponse> createOneSubscriberResponse = subscriberServiceImpl.createOneSubscriber(createOneSubscriberRequest);
        StepVerifier.create(createOneSubscriberResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneSubscriberException).verify();
    }

    @Test
    @Override
    public void testCreateOneSubscriber_FailedWhenRequestHasRequestorIsNull() {
        CreateOneSubscriberRequest createOneSubscriberRequest = CreateOneSubscriberRequest.builder().requestor(null).build();

        Mono<CreateOneSubscriberResponse> createOneSubscriberResponse = subscriberServiceImpl.createOneSubscriber(createOneSubscriberRequest);
        StepVerifier.create(createOneSubscriberResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneSubscriberException).verify();
    }

    @Test
    @Override
    public void testCreateOneSubscriber_FailedWhenRequestHasTargetIsNull() {
        CreateOneSubscriberRequest createOneSubscriberRequest = CreateOneSubscriberRequest.builder().target(null).build();

        Mono<CreateOneSubscriberResponse> createOneSubscriberResponse = subscriberServiceImpl.createOneSubscriber(createOneSubscriberRequest);
        StepVerifier.create(createOneSubscriberResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneSubscriberException).verify();
    }

    @Test
    @Override
    public void testCreateOneSubscriber_FailedWhenRepositoryCannotSave() {
        CreateOneSubscriberRequest createOneSubscriberRequest = CreateOneSubscriberRequest.builder().requestor("u1").target("u2").build();

        SubscriberEntity createdSubscriberEntity = SubscriberEntity.builder().requestor(createOneSubscriberRequest.getRequestor()).target(createOneSubscriberRequest.getTarget()).build();

        Mockito.when(iSubscriberRepository.save(createdSubscriberEntity))
                .thenThrow(new CreateOneSubscriberException(new Throwable("some message")));

        Mono<CreateOneSubscriberResponse> createOneSubscriberResponse = subscriberServiceImpl.createOneSubscriber(createOneSubscriberRequest);
        StepVerifier.create(createOneSubscriberResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneSubscriberException).verify();
    }
}
