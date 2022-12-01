package com.example.friendmanagement.service.iService;

public interface ISubscriberServiceTest {
    void testCreateOneSubscriber_Success();
    void testCreateOneSubscriber_FailedWhenRequestIsNull();
    void testCreateOneSubscriber_FailedWhenRequestHasRequestorIsNull();
    void testCreateOneSubscriber_FailedWhenRequestHasTargetIsNull();
    void testCreateOneSubscriber_FailedWhenRepositoryCannotSave();
}
