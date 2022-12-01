package com.example.friendmanagement.controller.iController;

public interface IFriendControllerTest {
    void testCreateOneFriend_Success();
    void testCreateOneFriend_Failed();
    void testGetAllFriendsByEmail_Success();
    void testGetAllFriendsByEmail_Failed();
    void testGetCommonFriends_Success();
    void testGetCommonFriends_Failed();
}
