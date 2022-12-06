package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.CreateOneFriendRequest;
import com.example.friendmanagement.api.request.GetAllFriendsRequest;
import com.example.friendmanagement.api.request.GetCommonFriendsRequest;
import com.example.friendmanagement.api.response.CreateOneFriendResponse;
import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import com.example.friendmanagement.api.response.GetCommonFriendsResponse;
import com.example.friendmanagement.common.ErrorResponse;
import com.example.friendmanagement.controller.iController.IFriendController;
import com.example.friendmanagement.service.serviceImpl.FriendServiceImpl;
import com.example.friendmanagement.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = Constant.API_FRIEND)
public class FriendControllerImpl implements IFriendController {
    @Autowired
    private FriendServiceImpl friendServiceImpl;

    @Operation(summary = "Create friend connection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created friend connection successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CreateOneFriendResponse.class)) }
            ), @ApiResponse(responseCode = "500", description = "Created friend connection failed",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }
            )
    })
    @PostMapping(Constant.CREATE_ONE)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Mono<CreateOneFriendResponse> createOneFriend(@RequestBody @NonNull CreateOneFriendRequest createOneFriendRequest) {
        return friendServiceImpl.createOneFriend(createOneFriendRequest);
    }

    @Operation(summary = "Get friend list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got friend list successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GetAllFriendsResponse.class)) }
            ), @ApiResponse(responseCode = "500", description = "Got friend list failed",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }
            )
    })
    @PostMapping(Constant.GET_ALL_BY_EMAIL)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Mono<GetAllFriendsResponse> getAllFriendsByEmail(@RequestBody @NonNull GetAllFriendsRequest getAllFriendsRequest) {
        return friendServiceImpl.getAllFriendsByEmail(getAllFriendsRequest);
    }

    @Operation(summary = "Get common friend list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get common friend list successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GetCommonFriendsResponse.class)) }
            ), @ApiResponse(responseCode = "500", description = "Get common friend list failed",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }
    )
    })
    @PostMapping(Constant.GET_COMMON_FRIENDS)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Mono<GetCommonFriendsResponse> getCommonFriends(@RequestBody @NonNull GetCommonFriendsRequest getCommonFriendsRequest) {
        return friendServiceImpl.getCommonFriends(getCommonFriendsRequest);
    }
}
