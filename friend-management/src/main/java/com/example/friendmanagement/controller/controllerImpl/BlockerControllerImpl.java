package com.example.friendmanagement.controller.controllerImpl;

import com.example.friendmanagement.api.request.CreateOneBlockerRequest;
import com.example.friendmanagement.api.response.CreateOneBlockerResponse;
import com.example.friendmanagement.controller.iController.IBlockerController;
import com.example.friendmanagement.service.serviceImpl.BlockerServiceImpl;
import com.example.friendmanagement.util.Constant;
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

    @PostMapping(Constant.CREATE_ONE)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Mono<CreateOneBlockerResponse> createOneBlocker(@RequestBody @NonNull CreateOneBlockerRequest createOneBlockerRequest) {
        return blockerServiceImpl.createOneBlocker(createOneBlockerRequest);
    }
}
