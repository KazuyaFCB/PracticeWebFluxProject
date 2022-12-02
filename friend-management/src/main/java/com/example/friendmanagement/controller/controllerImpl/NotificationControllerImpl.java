package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.GetAllNotifiedEmailsRequest;
import com.example.friendmanagement.api.response.GetAllNotifiedEmailsResponse;
import com.example.friendmanagement.common.ErrorResponse;
import com.example.friendmanagement.controller.iController.INotificationController;
import com.example.friendmanagement.service.serviceImpl.NotificationServiceImpl;
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
@RequestMapping(value = Constant.API_NOTIFICATION)
public class NotificationControllerImpl implements INotificationController {
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @Operation(summary = "Get all notified email list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all notified email list successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GetAllNotifiedEmailsResponse.class)) }
            ), @ApiResponse(responseCode = "500", description = "Got all notified email list failed",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }
    )
    })
    @PostMapping(Constant.GET_ALL_NOTIFIED_EMAILS)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Mono<GetAllNotifiedEmailsResponse> getAllNotifiedEmails(@RequestBody @NonNull GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest) {
        return notificationServiceImpl.getAllNotifiedEmails(getAllNotifiedEmailsRequest);
    }

}
