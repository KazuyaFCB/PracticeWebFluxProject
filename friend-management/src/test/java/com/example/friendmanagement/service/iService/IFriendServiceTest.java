package com.example.friendmanagement.service.iService;

public interface IFriendServiceTest {
    void testCreateOneFriend_Success();
    void testCreateOneFriend_FailedWhenRequestIsNull();
    void testCreateOneFriend_FailedWhenRequestHasFriendIsNull();
    void testCreateOneFriend_FailedWhenRequestHasLengthIsNotEqualTwo();
    void testCreateOneFriend_FailedWhenRequestHasFriendContainsSameElement();
    void testCreateOneFriend_FailedWhenRequestHasFriendBlockEachOther();
    void testCreateOneFriend_FailedWhenRepositoryCannotSave();
    void testGetAllFriendsByEmail_Success();
    void testGetAllFriendsByEmail_FailedWhenRequestIsNull();
    void testGetAllFriendsByEmail_FailedWhenRequestHasEmailIsNull();
    void testGetAllFriendsByEmail_FailedWhenRepositoryCannotFindEmail1ByEmail2();
    void testGetAllFriendsByEmail_FailedWhenRepositoryCannotFindEmail2ByEmail1();
    void testGetCommonFriends_Success();
    void testGetCommonFriends_FailedWhenRequestIsNull();
    void testGetCommonFriends_FailedWhenRequestHasFriendIsNull();
    void testGetCommonFriends_FailedWhenRequestHasLengthIsNotEqualTwo();
}
