package com.example.friendmanagement.service.iService;

public interface IBlockerServiceTest {
    void testCreateOneBlocker_Success();
    void testCreateOneBlocker_FailedWhenRequestIsNull();
    void testCreateOneBlocker_FailedWhenRequestHasRequestorIsNull();
    void testCreateOneBlocker_FailedWhenRequestHasTargetIsNull();
    void testCreateOneBlocker_FailedWhenRepositoryCannotSave();
}
