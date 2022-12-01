package com.example.friendmanagement.service.iService;

public interface INotificationServiceTest {
    void testGetAllNotifiedEmails_Success();
    void testGetAllNotifiedEmails_FailedWhenRequestIsNull();
    void testGetAllNotifiedEmails_FailedWhenRequestHasSenderIsNull();
    void testGetAllNotifiedEmails_FailedWhenRequestHasTextIsNull();
    void testGetAllNotifiedEmails_FailedWhenCannotExecuteGetNotifiedEmailListFromOneEmailMethod();
    void testGetAllNotifiedEmails_FailedWhenFriendRepositoryCannotFindEmail1ByEmail2();
    void testGetAllNotifiedEmails_FailedWhenFriendRepositoryCannotFindEmail2ByEmail1();
    void testGetAllNotifiedEmails_FailedWhenSubscriberRepositoryCannotFindRequestorByTarget();
    void testGetAllNotifiedEmails_FailedWhenBlockerRepositoryCannotFindRequestorByTarget();

    void testGetAllNotifiedEmails_FailedWhenBlockerRepositoryCannotFindTargetByRequestor();
    void testFindMentionedEmails_Success();
}
