package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.CreateOneBlockerRequest;
import com.example.friendmanagement.api.response.CreateOneBlockerResponse;
import com.example.friendmanagement.common.ErrorResponse;
import com.example.friendmanagement.controller.iController.IBlockerControllerTest;
import com.example.friendmanagement.error.UnitTestException;
import com.example.friendmanagement.service.serviceImpl.BlockerServiceImpl;
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

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BlockerControllerImpl.class)
@Import(BlockerServiceImpl.class)
public class BlockerControllerImplTest implements IBlockerControllerTest {
    @MockBean
    private BlockerServiceImpl blockerServiceImpl;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Override
    public void testCreateOneBlocker_Success() {
        CreateOneBlockerRequest createOneBlockerRequest = CreateOneBlockerRequest.builder().requestor("u1").target("u2").build();
        CreateOneBlockerResponse expectedCreateOneBlockerResponse = CreateOneBlockerResponse.builder().success(true).build();

        Mockito.when(blockerServiceImpl.createOneBlocker(createOneBlockerRequest)).thenReturn(Mono.just(expectedCreateOneBlockerResponse));

        webTestClient.post()
                .uri(Constant.API_BLOCKER + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneBlockerRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateOneBlockerResponse.class).isEqualTo(expectedCreateOneBlockerResponse);

        Mockito.verify(blockerServiceImpl, times(1)).createOneBlocker(createOneBlockerRequest);
    }

    @Test
    @Override
    public void testCreateOneBlocker_Failed() {
        CreateOneBlockerRequest createOneBlockerRequest = CreateOneBlockerRequest.builder().requestor(null).target(null).build();
        ErrorResponse expectedCreateOneBlockerResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "some message");

        Mockito.when(blockerServiceImpl.createOneBlocker(createOneBlockerRequest)).thenThrow(new UnitTestException(new Throwable(expectedCreateOneBlockerResponse.getMessage())));

        webTestClient.post()
                .uri(Constant.API_BLOCKER + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneBlockerRequest))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.status").isEqualTo(expectedCreateOneBlockerResponse.getStatus())
                .jsonPath("$.message").isEqualTo(expectedCreateOneBlockerResponse.getMessage());

        Mockito.verify(blockerServiceImpl, times(1)).createOneBlocker(createOneBlockerRequest);
    }
}
