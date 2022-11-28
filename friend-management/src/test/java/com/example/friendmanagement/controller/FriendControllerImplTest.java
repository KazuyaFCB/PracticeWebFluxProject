package com.example.friendmanagement.controller;

import com.example.friendmanagement.api.request.CreateOneFriendRequest;
import com.example.friendmanagement.api.request.GetAllFriendsRequest;
import com.example.friendmanagement.api.request.GetCommonFriendsRequest;
import com.example.friendmanagement.api.response.CreateOneFriendResponse;
import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import com.example.friendmanagement.api.response.GetCommonFriendsResponse;
import com.example.friendmanagement.controller.controllerImpl.FriendControllerImpl;
import com.example.friendmanagement.model.FriendEntity;
import com.example.friendmanagement.repository.IBlockerRepository;
import com.example.friendmanagement.repository.IFriendRepository;
import com.example.friendmanagement.service.serviceImpl.FriendServiceImpl;
import com.example.friendmanagement.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = FriendControllerImpl.class)
@Import(FriendServiceImpl.class)
public class FriendControllerImplTest implements IFriendControllerTest {
    @MockBean
    IFriendRepository iFriendRepository;

    @MockBean
    IBlockerRepository iBlockerRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Override
    public void testCreateOneFriend_Success() {
        String[] inputFriends = {"u1", "u2"};

        // init request, response
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(inputFriends).build();
        CreateOneFriendResponse createOneFriendResponse = CreateOneFriendResponse.builder().success(true).build();

        // simulate repository
        FriendEntity createdFriendEntity = FriendEntity.builder().email1(inputFriends[0]).email2(inputFriends[1]).build();
        Mono<FriendEntity> createdFriendEntityMono = Mono.just(createdFriendEntity);
        Mockito.when(iFriendRepository.save(createdFriendEntity)).thenReturn(createdFriendEntityMono);

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneFriendRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateOneFriendResponse.class).isEqualTo(createOneFriendResponse);

        Mockito.verify(iFriendRepository, times(1)).save(createdFriendEntity);
    }

    @Test
    @Override
    public void testCreateOneFriend_FailedWhenInputFriendsIsNull() {
        String[] inputFriends = null;

        // init request, response
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(inputFriends).build();
        CreateOneFriendResponse createOneFriendResponse = CreateOneFriendResponse.builder().success(false).build();

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneFriendRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateOneFriendResponse.class).isEqualTo(createOneFriendResponse);
    }

    @Test
    @Override
    public void testCreateOneFriend_FailedWhenInputFriendsHaveLengthIsNotEqualTwo() {
        String[] inputFriends = {"u1", "u2", "u3"};

        // init request, response
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(inputFriends).build();
        CreateOneFriendResponse createOneFriendResponse = CreateOneFriendResponse.builder().success(false).build();

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneFriendRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateOneFriendResponse.class).isEqualTo(createOneFriendResponse);
    }

    @Test
    @Override
    public void testCreateOneFriend_FailedWhenInputFriendsHaveSameElement() {
        String[] inputFriends = {"u1", "u1"};

        // init request, response
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(inputFriends).build();
        CreateOneFriendResponse createOneFriendResponse = CreateOneFriendResponse.builder().success(false).build();

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneFriendRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateOneFriendResponse.class).isEqualTo(createOneFriendResponse);
    }

//    @Test
//    //@Override
//    public void testCreateOneFriend_FailedWhenInputFriendsIsDuplicatedWithDatabase() {
//        String[] inputFriends = {"u1", "u2"};
//
//        // init request, response
//        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(inputFriends).build();
//        CreateOneFriendResponse createOneFriendResponse = CreateOneFriendResponse.builder().success(false).build();
//
//        // simulate repository
//        FriendEntity createdFriendEntity = FriendEntity.builder().email1(inputFriends[0]).email2(inputFriends[1]).build();
//        Mockito.doThrow(CreateOneFriendException.class).when(friendRepository).save(createdFriendEntity).then();
//        //Mockito.when(friendRepository.save(createdFriendEntity)).thenThrow(new CreateOneFriendException(null));
//
//        // simulate result
//        webTestClient.post()
//                .uri(Constant.API_FRIEND + Constant.CREATE_ONE)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromObject(createOneFriendRequest))
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(CreateOneFriendResponse.class).isEqualTo(createOneFriendResponse);
//
//        Mockito.verify(friendRepository).save(createdFriendEntity);
//    }

    @Test
    @Override
    public void testGetAllFriendsByEmail_Success() {
        String inputEmail = "u1";
        List<String> outputEmailsFromEmail1 = List.of("u11", "u12");
        List<String> outputEmailsFromEmail2 = List.of("u13", "u14");
        List<String> outputEmails = List.of("u11", "u12", "u13", "u14");

        // init request, response
        GetAllFriendsRequest getAllFriendsRequest = GetAllFriendsRequest.builder().email(inputEmail).build();
        GetAllFriendsResponse getAllFriendsResponse = GetAllFriendsResponse.builder().success(true).friends(outputEmails).count(outputEmails.size()).build();

        // simulate repository
        Flux<String> email1EntitiesFlux = Flux.fromIterable(outputEmailsFromEmail1);
        Flux<String> email2EntitiesFlux = Flux.fromIterable(outputEmailsFromEmail2);
        Mockito.when(iFriendRepository.findEmail1ByEmail2(inputEmail)).thenReturn(email1EntitiesFlux);
        Mockito.when(iFriendRepository.findEmail2ByEmail1(inputEmail)).thenReturn(email2EntitiesFlux);

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.GET_ALL_BY_EMAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getAllFriendsRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetAllFriendsResponse.class).isEqualTo(getAllFriendsResponse);

        Mockito.verify(iFriendRepository, times(1)).findEmail1ByEmail2(inputEmail);
        Mockito.verify(iFriendRepository, times(1)).findEmail2ByEmail1(inputEmail);
    }

    @Test
    @Override
    public void testGetAllFriendsByEmail_FailedWhenInputEmailIsNull() {
        String inputEmail = null;
        List<String> outputEmails = Collections.emptyList();

        // init request, response
        GetAllFriendsRequest getAllFriendsRequest = GetAllFriendsRequest.builder().email(inputEmail).build();
        GetAllFriendsResponse getAllFriendsResponse = GetAllFriendsResponse.builder().success(false).friends(outputEmails).count(0).build();

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.GET_ALL_BY_EMAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getAllFriendsRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetAllFriendsResponse.class).isEqualTo(getAllFriendsResponse);
    }

    @Test
    @Override
    public void testGetCommonFriends_Success() {

        String[] inputFriends = {"u1", "u2"};
        List<String> outputEmails = List.of("u_common");

        // init request, response
        GetCommonFriendsRequest getCommonFriendsRequest = GetCommonFriendsRequest.builder().friends(inputFriends).build();
        GetCommonFriendsResponse getCommonFriendsResponse = GetCommonFriendsResponse.builder().success(true).friends(outputEmails).count(outputEmails.size()).build();

        // simulate repository
        Flux<String> outputEmailsFlux = Flux.fromIterable(outputEmails);
        Mockito.when(iFriendRepository.findCommonEmail(inputFriends[0], inputFriends[1])).thenReturn(outputEmailsFlux);

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.GET_COMMON_FRIENDS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getCommonFriendsRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetCommonFriendsResponse.class).isEqualTo(getCommonFriendsResponse);

        Mockito.verify(iFriendRepository, times(1)).findCommonEmail(inputFriends[0], inputFriends[1]);
    }

    @Test
    @Override
    public void testGetCommonFriends_FailedWhenInputFriendsIsNull() {

        String[] inputFriends = null;
        List<String> outputEmails = Collections.emptyList();

        // init request, response
        GetCommonFriendsRequest getCommonFriendsRequest = GetCommonFriendsRequest.builder().friends(inputFriends).build();
        GetCommonFriendsResponse getCommonFriendsResponse = GetCommonFriendsResponse.builder().success(false).friends(outputEmails).count(0).build();

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.GET_COMMON_FRIENDS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getCommonFriendsRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetCommonFriendsResponse.class).isEqualTo(getCommonFriendsResponse);
    }

    @Test
    @Override
    public void testGetCommonFriends_FailedWhenInputFriendsHaveLengthIsNotEqualTwo() {

        String[] inputFriends = {"u1", "u2", "u3"};
        List<String> outputEmails = Collections.emptyList();

        // init request, response
        GetCommonFriendsRequest getCommonFriendsRequest = GetCommonFriendsRequest.builder().friends(inputFriends).build();
        GetCommonFriendsResponse getCommonFriendsResponse = GetCommonFriendsResponse.builder().success(false).friends(outputEmails).count(0).build();

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.GET_COMMON_FRIENDS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getCommonFriendsRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetCommonFriendsResponse.class).isEqualTo(getCommonFriendsResponse);
    }
}
