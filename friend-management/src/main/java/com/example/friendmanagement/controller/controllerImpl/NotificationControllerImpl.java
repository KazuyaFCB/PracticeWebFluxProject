package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.GetAllNotifiedEmailsRequest;
import com.example.friendmanagement.api.response.GetAllNotifiedEmailsResponse;
import com.example.friendmanagement.controller.iController.INotificationController;
import com.example.friendmanagement.service.serviceImpl.NotificationServiceImpl;
import com.example.friendmanagement.util.Constant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping(value = Constant.API_NOTIFICATION)
public class NotificationControllerImpl implements INotificationController {
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @PostMapping(Constant.GET_ALL_NOTIFIED_EMAILS)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Mono<GetAllNotifiedEmailsResponse> getAllNotifiedEmails(@RequestBody @NonNull GetAllNotifiedEmailsRequest getAllNotifiedEmailsRequest) {
        return notificationServiceImpl.getAllNotifiedEmails(getAllNotifiedEmailsRequest);
    }

}
