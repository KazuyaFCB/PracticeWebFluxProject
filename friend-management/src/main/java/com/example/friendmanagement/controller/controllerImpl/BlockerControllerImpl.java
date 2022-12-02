package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.CreateOneBlockerRequest;
import com.example.friendmanagement.api.response.CreateOneBlockerResponse;
import com.example.friendmanagement.common.ErrorResponse;
import com.example.friendmanagement.controller.iController.IBlockerController;
import com.example.friendmanagement.service.serviceImpl.BlockerServiceImpl;
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
@RequestMapping(value = Constant.API_BLOCKER)
public class BlockerControllerImpl implements IBlockerController {
    @Autowired
    private BlockerServiceImpl blockerServiceImpl;

    @Operation(summary = "Create blocker connection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created blocker connection successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CreateOneBlockerResponse.class)) }
            ), @ApiResponse(responseCode = "500", description = "Created blocker connection failed",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }
    )
    })
    @PostMapping(Constant.CREATE_ONE)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Mono<CreateOneBlockerResponse> createOneBlocker(@RequestBody @NonNull CreateOneBlockerRequest createOneBlockerRequest) {
        return blockerServiceImpl.createOneBlocker(createOneBlockerRequest);
    }
}
