package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.CreateOneSubscriberRequest;
import com.example.friendmanagement.api.response.CreateOneSubscriberResponse;
import com.example.friendmanagement.common.ErrorResponse;
import com.example.friendmanagement.controller.iController.ISubscriberControllerTest;
import com.example.friendmanagement.error.UnitTestException;
import com.example.friendmanagement.service.serviceImpl.SubscriberServiceImpl;
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
@WebFluxTest(controllers = SubscriberControllerImpl.class)
@Import(SubscriberServiceImpl.class)
public class SubscriberControllerImplTest implements ISubscriberControllerTest {
    @MockBean
    private SubscriberServiceImpl subscriberServiceImpl;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Override
    public void testCreateOneSubscriber_Success() {
        CreateOneSubscriberRequest createOneSubscriberRequest = CreateOneSubscriberRequest.builder().requestor("u1").target("u2").build();
        CreateOneSubscriberResponse expectedCreateOneSubscriberResponse = CreateOneSubscriberResponse.builder().success(true).build();

        Mockito.when(subscriberServiceImpl.createOneSubscriber(createOneSubscriberRequest)).thenReturn(Mono.just(expectedCreateOneSubscriberResponse));

        webTestClient.post()
                .uri(Constant.API_SUBSCRIBER + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneSubscriberRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateOneSubscriberResponse.class).isEqualTo(expectedCreateOneSubscriberResponse);

        Mockito.verify(subscriberServiceImpl, times(1)).createOneSubscriber(createOneSubscriberRequest);
    }

    @Test
    @Override
    public void testCreateOneSubscriber_Failed() {
        CreateOneSubscriberRequest createOneSubscriberRequest = CreateOneSubscriberRequest.builder().requestor(null).target(null).build();
        ErrorResponse expectedCreateOneSubscriberResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "some message");

        Mockito.when(subscriberServiceImpl.createOneSubscriber(createOneSubscriberRequest)).thenThrow(new UnitTestException(new Throwable(expectedCreateOneSubscriberResponse.getMessage())));

        webTestClient.post()
                .uri(Constant.API_SUBSCRIBER + Constant.CREATE_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(createOneSubscriberRequest))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.status").isEqualTo(expectedCreateOneSubscriberResponse.getStatus())
                .jsonPath("$.message").isEqualTo(expectedCreateOneSubscriberResponse.getMessage());

        Mockito.verify(subscriberServiceImpl, times(1)).createOneSubscriber(createOneSubscriberRequest);
    }
}
