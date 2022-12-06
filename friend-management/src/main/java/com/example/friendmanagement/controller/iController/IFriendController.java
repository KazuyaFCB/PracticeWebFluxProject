package com.example.friendmanagement.controller.iController;

import com.example.friendmanagement.api.request.CreateOneFriendRequest;
import com.example.friendmanagement.api.request.GetAllFriendsRequest;
import com.example.friendmanagement.api.request.GetCommonFriendsRequest;
import com.example.friendmanagement.api.response.CreateOneFriendResponse;
import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import com.example.friendmanagement.api.response.GetCommonFriendsResponse;
import reactor.core.publisher.Mono;

public interface IFriendController {
    Mono<CreateOneFriendResponse> createOneFriend(CreateOneFriendRequest createOneFriendRequest);
    Mono<GetAllFriendsResponse> getAllFriendsByEmail(GetAllFriendsRequest getAllFriendsRequest);
    Mono<GetCommonFriendsResponse> getCommonFriends(GetCommonFriendsRequest getCommonFriendsRequest);
}
