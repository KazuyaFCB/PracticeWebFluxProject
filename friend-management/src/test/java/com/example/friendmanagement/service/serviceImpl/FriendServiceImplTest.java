package com.example.friendmanagement.service.serviceImpl;

import com.example.friendmanagement.api.request.CreateOneFriendRequest;
import com.example.friendmanagement.api.request.GetAllFriendsRequest;
import com.example.friendmanagement.api.request.GetCommonFriendsRequest;
import com.example.friendmanagement.api.response.CreateOneFriendResponse;
import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import com.example.friendmanagement.api.response.GetCommonFriendsResponse;
import com.example.friendmanagement.error.CreateOneFriendException;
import com.example.friendmanagement.error.GetAllFriendsException;
import com.example.friendmanagement.error.GetCommonFriendsException;
import com.example.friendmanagement.model.FriendEntity;
import com.example.friendmanagement.repository.IBlockerRepository;
import com.example.friendmanagement.repository.IFriendRepository;
import com.example.friendmanagement.service.iService.IFriendServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;


@ExtendWith(SpringExtension.class)
@Slf4j
public class FriendServiceImplTest implements IFriendServiceTest {
    @InjectMocks
    private FriendServiceImpl friendServiceImpl;

    @Mock
    private IBlockerRepository iBlockerRepository;

    @Mock
    private IFriendRepository iFriendRepository;

    @Test
    @Override
    public void testCreateOneFriend_Success() {
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(new String[]{"u1", "u2"}).build();
        CreateOneFriendResponse expectedCreateOneFriendResponse = CreateOneFriendResponse.builder().success(true).build();

        FriendEntity createdFriendEntity = FriendEntity.builder().email1(createOneFriendRequest.getFriends()[0]).email2(createOneFriendRequest.getFriends()[1]).build();

        Mockito.when(iBlockerRepository.countEmailBlockEachOther(createOneFriendRequest.getFriends()[0], createOneFriendRequest.getFriends()[1]))
                .thenReturn(Mono.just(0));
        Mockito.when(iFriendRepository.save(createdFriendEntity))
                .thenReturn(Mono.just(createdFriendEntity));

        Mono<CreateOneFriendResponse> createOneFriendResponse = friendServiceImpl.createOneFriend(createOneFriendRequest);
        StepVerifier.create(createOneFriendResponse)
                .consumeNextWith(actualCreateOneFriendResponse -> Assertions.assertEquals(expectedCreateOneFriendResponse.isSuccess(), actualCreateOneFriendResponse.isSuccess()))
                .verifyComplete();
    }

    @Test
    @Override
    public void testCreateOneFriend_FailedWhenRequestIsNull() {
        CreateOneFriendRequest createOneFriendRequest = null;

        Mono<CreateOneFriendResponse> createOneFriendResponse = friendServiceImpl.createOneFriend(createOneFriendRequest);
        StepVerifier.create(createOneFriendResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneFriendException).verify();
    }

    @Test
    @Override
    public void testCreateOneFriend_FailedWhenRequestHasFriendIsNull() {
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(null).build();

        Mono<CreateOneFriendResponse> createOneFriendResponse = friendServiceImpl.createOneFriend(createOneFriendRequest);
        StepVerifier.create(createOneFriendResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneFriendException).verify();
    }

    @Test
    @Override
    public void testCreateOneFriend_FailedWhenRequestHasLengthIsNotEqualTwo() {
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(new String[]{"u1", "u2", "u3"}).build();

        Mono<CreateOneFriendResponse> createOneFriendResponse = friendServiceImpl.createOneFriend(createOneFriendRequest);
        StepVerifier.create(createOneFriendResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneFriendException).verify();
    }

    @Test
    @Override
    public void testCreateOneFriend_FailedWhenRequestHasFriendContainsSameElement() {
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(new String[]{"u1", "u1"}).build();

        Mono<CreateOneFriendResponse> createOneFriendResponse = friendServiceImpl.createOneFriend(createOneFriendRequest);
        StepVerifier.create(createOneFriendResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneFriendException).verify();
    }

    @Test
    @Override
    public void testCreateOneFriend_FailedWhenRequestHasFriendBlockEachOther() {
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(new String[]{"u1", "u2"}).build();

        Mockito.when(iBlockerRepository.countEmailBlockEachOther(createOneFriendRequest.getFriends()[0], createOneFriendRequest.getFriends()[1]))
                .thenReturn(Mono.just(1));

        Mono<CreateOneFriendResponse> createOneFriendResponse = friendServiceImpl.createOneFriend(createOneFriendRequest);
        StepVerifier.create(createOneFriendResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneFriendException).verify();
    }

    @Test
    @Override
    public void testCreateOneFriend_FailedWhenRepositoryCannotSave() {
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(new String[]{"u1", "u2"}).build();

        FriendEntity createdFriendEntity = FriendEntity.builder().email1(createOneFriendRequest.getFriends()[0]).email2(createOneFriendRequest.getFriends()[1]).build();

        Mockito.when(iBlockerRepository.countEmailBlockEachOther(createOneFriendRequest.getFriends()[0], createOneFriendRequest.getFriends()[1]))
                .thenReturn(Mono.just(0));
        Mockito.when(iFriendRepository.save(createdFriendEntity))
                .thenThrow(new CreateOneFriendException(new Throwable("some message")));

        Mono<CreateOneFriendResponse> createOneFriendResponse = friendServiceImpl.createOneFriend(createOneFriendRequest);
        StepVerifier.create(createOneFriendResponse).expectErrorMatches(throwable -> throwable instanceof CreateOneFriendException).verify();
    }

    @Test
    @Override
    public void testGetAllFriendsByEmail_Success() {
        List<String> outputEmailsFromEmail1 = List.of("u11", "u12");
        List<String> outputEmailsFromEmail2 = List.of("u13", "u14");
        List<String> outputEmails = List.of("u11", "u12", "u13", "u14");

        GetAllFriendsRequest getAllFriendsRequest = GetAllFriendsRequest.builder().email("u1").build();
        GetAllFriendsResponse expectedGetAllFriendsResponse = GetAllFriendsResponse.builder().success(true).friends(outputEmails).count(outputEmails.size()).build();

        Mockito.when(iFriendRepository.findEmail1ByEmail2(getAllFriendsRequest.getEmail())).thenReturn(Flux.fromIterable(outputEmailsFromEmail1));
        Mockito.when(iFriendRepository.findEmail2ByEmail1(getAllFriendsRequest.getEmail())).thenReturn(Flux.fromIterable(outputEmailsFromEmail2));

        Mono<GetAllFriendsResponse> getAllFriendsResponse = friendServiceImpl.getAllFriendsByEmail(getAllFriendsRequest);
        StepVerifier.create(getAllFriendsResponse)
                .consumeNextWith(actualGetAllFriendsResponse -> Assertions.assertEquals(expectedGetAllFriendsResponse, actualGetAllFriendsResponse))
                .verifyComplete();
    }

    @Test
    @Override
    public void testGetAllFriendsByEmail_FailedWhenRequestIsNull() {
        GetAllFriendsRequest getAllFriendsRequest = null;

        Mono<GetAllFriendsResponse> getAllFriendsResponse = friendServiceImpl.getAllFriendsByEmail(getAllFriendsRequest);
        StepVerifier.create(getAllFriendsResponse).expectErrorMatches(throwable -> throwable instanceof GetAllFriendsException).verify();
    }

    @Test
    @Override
    public void testGetAllFriendsByEmail_FailedWhenRequestHasEmailIsNull() {
        GetAllFriendsRequest getAllFriendsRequest = GetAllFriendsRequest.builder().email(null).build();

        Mono<GetAllFriendsResponse> getAllFriendsResponse = friendServiceImpl.getAllFriendsByEmail(getAllFriendsRequest);
        StepVerifier.create(getAllFriendsResponse).expectErrorMatches(throwable -> throwable instanceof GetAllFriendsException).verify();
    }

    @Test
    @Override
    public void testGetAllFriendsByEmail_FailedWhenRepositoryCannotFindEmail1ByEmail2() {
        GetAllFriendsRequest getAllFriendsRequest = GetAllFriendsRequest.builder().email("u1").build();

        Mockito.when(iFriendRepository.findEmail1ByEmail2(getAllFriendsRequest.getEmail()))
                .thenThrow(new GetAllFriendsException(new Throwable("some message")));

        Mono<GetAllFriendsResponse> getAllFriendsResponse = friendServiceImpl.getAllFriendsByEmail(getAllFriendsRequest);
        StepVerifier.create(getAllFriendsResponse).expectErrorMatches(throwable -> throwable instanceof GetAllFriendsException).verify();
    }

    @Test
    @Override
    public void testGetAllFriendsByEmail_FailedWhenRepositoryCannotFindEmail2ByEmail1() {
        GetAllFriendsRequest getAllFriendsRequest = GetAllFriendsRequest.builder().email("u1").build();

        Mockito.when(iFriendRepository.findEmail2ByEmail1(getAllFriendsRequest.getEmail()))
                .thenThrow(new GetAllFriendsException(new Throwable("some message")));

        Mono<GetAllFriendsResponse> getAllFriendsResponse = friendServiceImpl.getAllFriendsByEmail(getAllFriendsRequest);
        StepVerifier.create(getAllFriendsResponse).expectErrorMatches(throwable -> throwable instanceof GetAllFriendsException).verify();
    }

    @Test
    @Override
    public void testGetCommonFriends_Success() {
        List<String> outputEmails = List.of("u11", "u12", "u13", "u14");

        GetCommonFriendsRequest getCommonFriendsRequest = GetCommonFriendsRequest.builder().friends(new String[] { "u1", "u2" }).build();
        GetCommonFriendsResponse expectedGetCommonFriendsResponse = GetCommonFriendsResponse.builder().success(true).friends(outputEmails).count(outputEmails.size()).build();

        Mockito.when(iFriendRepository.findCommonEmail(getCommonFriendsRequest.getFriends()[0], getCommonFriendsRequest.getFriends()[1])).thenReturn(Flux.fromIterable(outputEmails));

        Mono<GetCommonFriendsResponse> getAllFriendsResponse = friendServiceImpl.getCommonFriends(getCommonFriendsRequest);
        StepVerifier.create(getAllFriendsResponse)
                .consumeNextWith(actualGetAllFriendsResponse -> Assertions.assertEquals(expectedGetCommonFriendsResponse, actualGetAllFriendsResponse))
                .verifyComplete();
    }

    @Test
    @Override
    public void testGetCommonFriends_FailedWhenRequestIsNull() {
        Mono<GetCommonFriendsResponse> getCommonFriendsResponse = friendServiceImpl.getCommonFriends(null);

        StepVerifier.create(getCommonFriendsResponse).expectErrorMatches(throwable -> throwable instanceof GetCommonFriendsException).verify();
    }

    @Test
    @Override
    public void testGetCommonFriends_FailedWhenRequestHasFriendIsNull() {
        GetCommonFriendsRequest getCommonFriendsRequest = GetCommonFriendsRequest.builder().friends(null).build();

        Mono<GetCommonFriendsResponse> getCommonFriendsResponse = friendServiceImpl.getCommonFriends(getCommonFriendsRequest);
        StepVerifier.create(getCommonFriendsResponse).expectErrorMatches(throwable -> throwable instanceof GetCommonFriendsException).verify();
    }

    @Test
    @Override
    public void testGetCommonFriends_FailedWhenRequestHasLengthIsNotEqualTwo() {
        GetCommonFriendsRequest getCommonFriendsRequest = GetCommonFriendsRequest.builder().friends(new String[]{"u1", "u2", "u3"}).build();

        Mono<GetCommonFriendsResponse> getCommonFriendsResponse = friendServiceImpl.getCommonFriends(getCommonFriendsRequest);
        StepVerifier.create(getCommonFriendsResponse).expectErrorMatches(throwable -> throwable instanceof GetCommonFriendsException).verify();
    }


}
