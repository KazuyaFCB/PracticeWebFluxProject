package com.example.friendmanagement.controller;

public interface IFriendControllerTest {
    void testCreateOneFriend_Success();
    void testCreateOneFriend_FailedWhenInputFriendsIsNull();
    void testCreateOneFriend_FailedWhenInputFriendsHaveLengthIsNotEqualTwo();
    void testCreateOneFriend_FailedWhenInputFriendsHaveSameElement();
//    void testCreateOneFriend_FailedWhenInputFriendsIsDuplicatedWithDatabase();
    void testGetAllFriendsByEmail_Success();
    void testGetAllFriendsByEmail_FailedWhenInputEmailIsNull();
    void testGetCommonFriends_Success();
    void testGetCommonFriends_FailedWhenInputFriendsIsNull();
    void testGetCommonFriends_FailedWhenInputFriendsHaveLengthIsNotEqualTwo();
}
