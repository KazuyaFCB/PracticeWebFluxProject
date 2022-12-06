package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.CreateOneSubscriberRequest;
import com.example.friendmanagement.api.response.CreateOneSubscriberResponse;
import com.example.friendmanagement.common.ErrorResponse;
import com.example.friendmanagement.controller.iController.ISubscriberController;
import com.example.friendmanagement.service.serviceImpl.SubscriberServiceImpl;
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
@RequestMapping(value = Constant.API_SUBSCRIBER)
public class SubscriberControllerImpl implements ISubscriberController {
    @Autowired
    private SubscriberServiceImpl subscriberServiceImpl;

    @Operation(summary = "Create subscriber connection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created subscriber connection successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CreateOneSubscriberResponse.class)) }
            ), @ApiResponse(responseCode = "500", description = "Created subscriber connection failed",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }
    )
    })
    @PostMapping(Constant.CREATE_ONE)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Mono<CreateOneSubscriberResponse> createOneSubscriber(@RequestBody @NonNull CreateOneSubscriberRequest createOneSubscriberRequest) {
        return subscriberServiceImpl.createOneSubscriber(createOneSubscriberRequest);
    }
}
