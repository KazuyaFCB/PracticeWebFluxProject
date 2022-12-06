package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.GetAllNotifiedEmailsRequest;
import com.example.friendmanagement.api.response.GetAllNotifiedEmailsResponse;
import com.example.friendmanagement.common.ErrorResponse;
import com.example.friendmanagement.controller.iController.INotificationControllerTest;
import com.example.friendmanagement.error.UnitTestException;
import com.example.friendmanagement.service.serviceImpl.NotificationServiceImpl;
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
@WebFluxTest(controllers = NotificationControllerImpl.class)
@Import(NotificationServiceImpl.class)
public class NotificationControllerImplTest implements INotificationControllerTest {
    @MockBean
    private NotificationServiceImpl notificationServiceImpl;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Override
    public void testGetAllNotifiedEmails_Success() {
        GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender("u1").text("u2").build();
        GetAllNotifiedEmailsResponse expectedGetAllNotifiedEmailsResponse = GetAllNotifiedEmailsResponse.builder().success(true).build();

        Mockito.when(notificationServiceImpl.getAllNotifiedEmails(getAllNotifiedEmailsRequest)).thenReturn(Mono.just(expectedGetAllNotifiedEmailsResponse));

        webTestClient.post()
                .uri(Constant.API_NOTIFICATION + Constant.GET_ALL_NOTIFIED_EMAILS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getAllNotifiedEmailsRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetAllNotifiedEmailsResponse.class).isEqualTo(expectedGetAllNotifiedEmailsResponse);

        Mockito.verify(notificationServiceImpl, times(1)).getAllNotifiedEmails(getAllNotifiedEmailsRequest);
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_Failed() {
        GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender(null).text(null).build();
        ErrorResponse expectedGetAllNotifiedEmailsResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "some message");

        Mockito.when(notificationServiceImpl.getAllNotifiedEmails(getAllNotifiedEmailsRequest)).thenThrow(new UnitTestException(new Throwable(expectedGetAllNotifiedEmailsResponse.getMessage())));

        webTestClient.post()
                .uri(Constant.API_NOTIFICATION + Constant.GET_ALL_NOTIFIED_EMAILS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getAllNotifiedEmailsRequest))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.status").isEqualTo(expectedGetAllNotifiedEmailsResponse.getStatus())
                .jsonPath("$.message").isEqualTo(expectedGetAllNotifiedEmailsResponse.getMessage());

        Mockito.verify(notificationServiceImpl, times(1)).getAllNotifiedEmails(getAllNotifiedEmailsRequest);
    }
}
