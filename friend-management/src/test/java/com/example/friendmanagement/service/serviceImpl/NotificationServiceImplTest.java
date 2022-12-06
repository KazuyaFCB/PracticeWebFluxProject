package com.example.friendmanagement.service.serviceImpl;

import com.example.friendmanagement.api.request.GetAllNotifiedEmailsRequest;
import com.example.friendmanagement.api.response.GetAllNotifiedEmailsResponse;
import com.example.friendmanagement.error.GetAllNotifiedEmailsException;
import com.example.friendmanagement.repository.IBlockerRepository;
import com.example.friendmanagement.repository.IFriendRepository;
import com.example.friendmanagement.repository.ISubscriberRepository;
import com.example.friendmanagement.service.iService.INotificationServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Slf4j
public class NotificationServiceImplTest implements INotificationServiceTest {
    @InjectMocks
    private NotificationServiceImpl notificationServiceImpl;

    @MockBean
    private NotificationServiceImpl notificationServiceImplMockBean;

    @Mock
    private IFriendRepository iFriendRepository;

    @Mock
    private ISubscriberRepository iSubscriberRepository;

    @Mock
    private IBlockerRepository iBlockerRepository;

    @Test
    @Override
    public void testGetAllNotifiedEmails_Success() {
        GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender("u1").text("u2").build();
        GetAllNotifiedEmailsResponse expectedGetAllNotifiedEmailsResponse = GetAllNotifiedEmailsResponse.builder().success(true).recipients(Arrays.asList("u11", "u12")).build();

        Mockito.when(iFriendRepository.findEmail1ByEmail2(getAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.just("u11", "u12"));
        Mockito.when(iFriendRepository.findEmail2ByEmail1(getAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iSubscriberRepository.findRequestorByTarget(getAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iBlockerRepository.findRequestorByTarget(getAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iBlockerRepository.findTargetByRequestor(getAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());

        Mono<GetAllNotifiedEmailsResponse> getAllNotifiedEmailsResponse = notificationServiceImpl.getAllNotifiedEmails(getAllNotifiedEmailsRequest);
        StepVerifier.create(getAllNotifiedEmailsResponse)
                .consumeNextWith(actualGetAllNotifiedEmailsResponse -> Assertions.assertEquals(expectedGetAllNotifiedEmailsResponse, actualGetAllNotifiedEmailsResponse))
                .verifyComplete();
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_FailedWhenRequestIsNull() {
        GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest = null;

        Mono<GetAllNotifiedEmailsResponse> getAllNotifiedEmailsResponse = notificationServiceImpl.getAllNotifiedEmails(getAllNotifiedEmailsRequest);
        StepVerifier.create(getAllNotifiedEmailsResponse).expectErrorMatches(throwable -> throwable instanceof GetAllNotifiedEmailsException).verify();
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_FailedWhenRequestHasSenderIsNull() {
        GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender(null).build();

        Mono<GetAllNotifiedEmailsResponse> getAllNotifiedEmailsResponse = notificationServiceImpl.getAllNotifiedEmails(getAllNotifiedEmailsRequest);
        StepVerifier.create(getAllNotifiedEmailsResponse).expectErrorMatches(throwable -> throwable instanceof GetAllNotifiedEmailsException).verify();
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_FailedWhenRequestHasTextIsNull() {
        GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().text(null).build();

        Mono<GetAllNotifiedEmailsResponse> getAllNotifiedEmailsResponse = notificationServiceImpl.getAllNotifiedEmails(getAllNotifiedEmailsRequest);
        StepVerifier.create(getAllNotifiedEmailsResponse).expectErrorMatches(throwable -> throwable instanceof GetAllNotifiedEmailsException).verify();
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_FailedWhenCannotExecuteGetNotifiedEmailListFromOneEmailMethod() {
        GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender("u1").text("u2").build();

        Mockito.when(notificationServiceImplMockBean.getNotifiedEmailListFromOneEmail(getAllNotifiedEmailsRequest.getSender(), getAllNotifiedEmailsRequest.getText()))
                .thenThrow(new GetAllNotifiedEmailsException(new Throwable("some message")));

        Mono<GetAllNotifiedEmailsResponse> getAllNotifiedEmailsResponse = notificationServiceImpl.getAllNotifiedEmails(getAllNotifiedEmailsRequest);
        StepVerifier.create(getAllNotifiedEmailsResponse).expectErrorMatches(throwable -> throwable instanceof GetAllNotifiedEmailsException).verify();
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_FailedWhenFriendRepositoryCannotFindEmail1ByEmail2() {
        GetAllNotifiedEmailsRequest getGetAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender("u1").text("u2").build();

        Mockito.when(iFriendRepository.findEmail1ByEmail2(getGetAllNotifiedEmailsRequest.getSender()))
                .thenThrow(new GetAllNotifiedEmailsException(new Throwable("some message")));

        Mono<GetAllNotifiedEmailsResponse> getNotifiedEmailListFromOneEmailResponse = notificationServiceImpl.getAllNotifiedEmails(getGetAllNotifiedEmailsRequest);
        StepVerifier.create(getNotifiedEmailListFromOneEmailResponse).expectErrorMatches(throwable -> throwable instanceof GetAllNotifiedEmailsException).verify();
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_FailedWhenFriendRepositoryCannotFindEmail2ByEmail1() {
        GetAllNotifiedEmailsRequest getGetAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender("u1").text("u2").build();

        Mockito.when(iFriendRepository.findEmail1ByEmail2(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iFriendRepository.findEmail2ByEmail1(getGetAllNotifiedEmailsRequest.getSender()))
                .thenThrow(new GetAllNotifiedEmailsException(new Throwable("some message")));

        Mono<GetAllNotifiedEmailsResponse> getNotifiedEmailListFromOneEmailResponse = notificationServiceImpl.getAllNotifiedEmails(getGetAllNotifiedEmailsRequest);
        StepVerifier.create(getNotifiedEmailListFromOneEmailResponse).expectErrorMatches(throwable -> throwable instanceof GetAllNotifiedEmailsException).verify();
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_FailedWhenSubscriberRepositoryCannotFindRequestorByTarget() {
        GetAllNotifiedEmailsRequest getGetAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender("u1").text("u2").build();

        Mockito.when(iFriendRepository.findEmail1ByEmail2(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iFriendRepository.findEmail2ByEmail1(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iSubscriberRepository.findRequestorByTarget(getGetAllNotifiedEmailsRequest.getSender()))
                .thenThrow(new GetAllNotifiedEmailsException(new Throwable("some message")));

        Mono<GetAllNotifiedEmailsResponse> getNotifiedEmailListFromOneEmailResponse = notificationServiceImpl.getAllNotifiedEmails(getGetAllNotifiedEmailsRequest);
        StepVerifier.create(getNotifiedEmailListFromOneEmailResponse).expectErrorMatches(throwable -> throwable instanceof GetAllNotifiedEmailsException).verify();
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_FailedWhenBlockerRepositoryCannotFindRequestorByTarget() {
        GetAllNotifiedEmailsRequest getGetAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender("u1").text("u2").build();

        Mockito.when(iFriendRepository.findEmail1ByEmail2(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iFriendRepository.findEmail2ByEmail1(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iSubscriberRepository.findRequestorByTarget(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iBlockerRepository.findRequestorByTarget(getGetAllNotifiedEmailsRequest.getSender()))
                .thenThrow(new GetAllNotifiedEmailsException(new Throwable("some message")));

        Mono<GetAllNotifiedEmailsResponse> getNotifiedEmailListFromOneEmailResponse = notificationServiceImpl.getAllNotifiedEmails(getGetAllNotifiedEmailsRequest);
        StepVerifier.create(getNotifiedEmailListFromOneEmailResponse).expectErrorMatches(throwable -> throwable instanceof GetAllNotifiedEmailsException).verify();
    }

    @Test
    @Override
    public void testGetAllNotifiedEmails_FailedWhenBlockerRepositoryCannotFindTargetByRequestor() {
        GetAllNotifiedEmailsRequest getGetAllNotifiedEmailsRequest = GetAllNotifiedEmailsRequest.builder().sender("u1").text("u2").build();

        Mockito.when(iFriendRepository.findEmail1ByEmail2(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iFriendRepository.findEmail2ByEmail1(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iSubscriberRepository.findRequestorByTarget(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iBlockerRepository.findRequestorByTarget(getGetAllNotifiedEmailsRequest.getSender()))
                .thenReturn(Flux.empty());
        Mockito.when(iBlockerRepository.findTargetByRequestor(getGetAllNotifiedEmailsRequest.getSender()))
                .thenThrow(new GetAllNotifiedEmailsException(new Throwable("some message")));

        Mono<GetAllNotifiedEmailsResponse> getNotifiedEmailListFromOneEmailResponse = notificationServiceImpl.getAllNotifiedEmails(getGetAllNotifiedEmailsRequest);
        StepVerifier.create(getNotifiedEmailListFromOneEmailResponse).expectErrorMatches(throwable -> throwable instanceof GetAllNotifiedEmailsException).verify();
    }

    @Test
    @Override
    public void testFindMentionedEmails_Success() {
        String text = "Hello World! kate@example.com andy@example.com";
        List<String> expectedFindMentionedEmailsResponse = Arrays.asList(" kate@example.com ", "andy@example.com");

        Flux<String> getFindMentionedEmailsResponse = notificationServiceImpl.findMentionedEmails(text);
        StepVerifier.create(getFindMentionedEmailsResponse)
                .consumeNextWith(actualFindMentionedEmailsResponse -> Assertions.assertEquals(expectedFindMentionedEmailsResponse.get(0), actualFindMentionedEmailsResponse))
                .consumeNextWith(actualFindMentionedEmailsResponse -> Assertions.assertEquals(expectedFindMentionedEmailsResponse.get(1), actualFindMentionedEmailsResponse))
                .verifyComplete();
    }
}
