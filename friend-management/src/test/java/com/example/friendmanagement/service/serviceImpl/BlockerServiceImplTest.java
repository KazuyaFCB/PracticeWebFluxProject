package com.example.friendmanagement.service.serviceImpl;

import com.example.friendmanagement.api.request.CreateOneBlockerRequest;
import com.example.friendmanagement.api.response.CreateOneBlockerResponse;
import com.example.friendmanagement.error.CreateOneBlockerException;
import com.example.friendmanagement.model.BlockerEntity;
import com.example.friendmanagement.repository.IBlockerRepository;
import com.example.friendmanagement.service.iService.IBlockerServiceTest;
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
public class BlockerServiceImplTest implements IBlockerServiceTest {
    @InjectMocks
    private BlockerServiceImpl blockerServiceImpl;

    @Mock
    private IBlockerRepository iBlockerRepository;

    @Test
    @Override
    public void testCreateOneBlocker_Success() {
        CreateOneBlockerRequest createOneBlockerRequest = CreateOneBlockerRequest.builder().requestor("u1").target("u2").build();
        CreateOneBlockerResponse expectedCreateOneBlockerResponse = CreateOneBlockerResponse.builder().success(true).build();

        BlockerEntity createdBlockerEntity = BlockerEntity.builder().requestor(createOneBlockerRequest.getRequestor()).target(createOneBlockerRequest.getTarget()).build();

        Mockito.when(iBlockerRepository.save(createdBlockerEntity))
                .thenReturn(Mono.just(createdBlockerEntity));

        Mono<CreateOneBlockerResponse> createOneBlockerResponse = blockerServiceImpl.createOneBlocker(createOneBlockerRequest);
        StepVerifier.create(createOneBlockerResponse)
                .consumeNextWith(actualCreateOneBlockerResponse -> Assertions.assertEquals(expectedCreateOneBlockerResponse.isSuccess(), actualCreateOneBlockerResponse.isSuccess()))
                .verifyComplete();
    }

    @Test
    @Override
    public void testCreateOneBlocker_FailedWhenRequestIsNull() {
        CreateOneBlockerRequest createOneBlockerRequest = null;

        Mono<CreateOneBlockerResponse> createOneBlockerResponse = blockerServiceImpl.createOneBlocker(createOneBlockerRequest);
        StepVerifier.create(createOneBlockerResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneBlockerException).verify();
    }

    @Test
    @Override
    public void testCreateOneBlocker_FailedWhenRequestHasRequestorIsNull() {
        CreateOneBlockerRequest createOneBlockerRequest = CreateOneBlockerRequest.builder().requestor(null).build();

        Mono<CreateOneBlockerResponse> createOneBlockerResponse = blockerServiceImpl.createOneBlocker(createOneBlockerRequest);
        StepVerifier.create(createOneBlockerResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneBlockerException).verify();
    }

    @Test
    @Override
    public void testCreateOneBlocker_FailedWhenRequestHasTargetIsNull() {
        CreateOneBlockerRequest createOneBlockerRequest = CreateOneBlockerRequest.builder().target(null).build();

        Mono<CreateOneBlockerResponse> createOneBlockerResponse = blockerServiceImpl.createOneBlocker(createOneBlockerRequest);
        StepVerifier.create(createOneBlockerResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneBlockerException).verify();
    }

    @Test
    @Override
    public void testCreateOneBlocker_FailedWhenRepositoryCannotSave() {
        CreateOneBlockerRequest createOneBlockerRequest = CreateOneBlockerRequest.builder().requestor("u1").target("u2").build();

        BlockerEntity createdBlockerEntity = BlockerEntity.builder().requestor(createOneBlockerRequest.getRequestor()).target(createOneBlockerRequest.getTarget()).build();

        Mockito.when(iBlockerRepository.save(createdBlockerEntity))
                .thenThrow(new CreateOneBlockerException(new Throwable("some message")));

        Mono<CreateOneBlockerResponse> createOneBlockerResponse = blockerServiceImpl.createOneBlocker(createOneBlockerRequest);
        StepVerifier.create(createOneBlockerResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneBlockerException).verify();
    }
}
