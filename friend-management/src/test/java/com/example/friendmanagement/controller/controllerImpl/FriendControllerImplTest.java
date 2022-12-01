package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.CreateOneFriendRequest;
import com.example.friendmanagement.api.request.GetAllFriendsRequest;
import com.example.friendmanagement.api.request.GetCommonFriendsRequest;
import com.example.friendmanagement.api.response.CreateOneFriendResponse;
import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import com.example.friendmanagement.api.response.GetCommonFriendsResponse;
import com.example.friendmanagement.common.ErrorResponse;
import com.example.friendmanagement.controller.iController.IFriendControllerTest;
import com.example.friendmanagement.error.UnitTestException;
import com.example.friendmanagement.service.serviceImpl.FriendServiceImpl;
import com.example.friendmanagement.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = FriendControllerImpl.class)
@Import({FriendServiceImpl.class})
public class FriendControllerImplTest implements IFriendControllerTest {
    @MockBean
    private FriendServiceImpl friendServiceImpl;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Override
    public void testCreateOneFriend_Success() {
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(new String[] {"u1", "u2"}).build();
        CreateOneFriendResponse expectedCreateOneFriendResponse = CreateOneFriendResponse.builder().success(true).build();

        Mockito.when(friendServiceImpl.createOneFriend(createOneFriendRequest)).thenReturn(Mono.just(expectedCreateOneFriendResponse));

        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneFriendRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateOneFriendResponse.class).isEqualTo(expectedCreateOneFriendResponse);

        Mockito.verify(friendServiceImpl, times(1)).createOneFriend(createOneFriendRequest);
    }

    @Test
    @Override
    public void testCreateOneFriend_Failed() {
        CreateOneFriendRequest createOneFriendRequest = CreateOneFriendRequest.builder().friends(new String[] {"u1", "u2", "u3"}).build();
        ErrorResponse expectedCreateOneFriendResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "some message");

        Mockito.when(friendServiceImpl.createOneFriend(createOneFriendRequest)).thenThrow(new UnitTestException(new Throwable(expectedCreateOneFriendResponse.getMessage())));

        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneFriendRequest))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.status").isEqualTo(expectedCreateOneFriendResponse.getStatus())
                .jsonPath("$.message").isEqualTo(expectedCreateOneFriendResponse.getMessage());

        Mockito.verify(friendServiceImpl, times(1)).createOneFriend(createOneFriendRequest);
    }

    @Test
    @Override
    public void testGetAllFriendsByEmail_Success() {
        GetAllFriendsRequest getAllFriendsRequest = GetAllFriendsRequest.builder().email("u1").build();
        GetAllFriendsResponse expectedGetAllFriendsResponse = GetAllFriendsResponse.builder().success(true).friends(List.of("u11", "u12", "u13", "u14")).count(4).build();

        Mockito.when(friendServiceImpl.getAllFriendsByEmail(getAllFriendsRequest)).thenReturn(Mono.just(expectedGetAllFriendsResponse));

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.GET_ALL_BY_EMAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getAllFriendsRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetAllFriendsResponse.class).isEqualTo(expectedGetAllFriendsResponse);

        Mockito.verify(friendServiceImpl, times(1)).getAllFriendsByEmail(getAllFriendsRequest);
    }

    @Test
    @Override
    public void testGetAllFriendsByEmail_Failed() {
        GetAllFriendsRequest getAllFriendsRequest = GetAllFriendsRequest.builder().email(null).build();
        ErrorResponse expectedGetAllFriendsResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "some message");

        Mockito.when(friendServiceImpl.getAllFriendsByEmail(getAllFriendsRequest)).thenThrow(new UnitTestException(new Throwable(expectedGetAllFriendsResponse.getMessage())));

        // simulate result
        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.GET_ALL_BY_EMAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getAllFriendsRequest))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.status").isEqualTo(expectedGetAllFriendsResponse.getStatus())
                .jsonPath("$.message").isEqualTo(expectedGetAllFriendsResponse.getMessage());

        Mockito.verify(friendServiceImpl, times(1)).getAllFriendsByEmail(getAllFriendsRequest);
    }

    @Test
    @Override
    public void testGetCommonFriends_Success() {
        GetCommonFriendsRequest getCommonFriendsRequest = GetCommonFriendsRequest.builder().friends(new String[]{"u1", "u2"}).build();
        GetCommonFriendsResponse expectedGetCommonFriendsResponse = GetCommonFriendsResponse.builder().success(true).friends(List.of("u11", "u12", "u13", "u14")).count(4).build();

        Mockito.when(friendServiceImpl.getCommonFriends(getCommonFriendsRequest)).thenReturn(Mono.just(expectedGetCommonFriendsResponse));

        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.GET_COMMON_FRIENDS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getCommonFriendsRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetCommonFriendsResponse.class).isEqualTo(expectedGetCommonFriendsResponse);

        Mockito.verify(friendServiceImpl, times(1)).getCommonFriends(getCommonFriendsRequest);
    }

    @Test
    @Override
    public void testGetCommonFriends_Failed() {
        GetCommonFriendsRequest getCommonFriendsRequest = GetCommonFriendsRequest.builder().friends(new String[]{"u1", "u2", "u3"}).build();
        ErrorResponse expectedGetCommonFriendsResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "some message");

        Mockito.when(friendServiceImpl.getCommonFriends(getCommonFriendsRequest)).thenThrow(new UnitTestException(new Throwable(expectedGetCommonFriendsResponse.getMessage())));

        webTestClient.post()
                .uri(Constant.API_FRIEND + Constant.GET_COMMON_FRIENDS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getCommonFriendsRequest))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.status").isEqualTo(expectedGetCommonFriendsResponse.getStatus())
                .jsonPath("$.message").isEqualTo(expectedGetCommonFriendsResponse.getMessage());

        Mockito.verify(friendServiceImpl, times(1)).getCommonFriends(getCommonFriendsRequest);
    }
}
