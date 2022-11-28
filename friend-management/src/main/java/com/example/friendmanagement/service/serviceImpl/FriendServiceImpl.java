package com.example.friendmanagement.service.serviceImpl;

import com.example.friendmanagement.api.request.CreateOneFriendRequest;
import com.example.friendmanagement.api.request.GetAllFriendsRequest;
import com.example.friendmanagement.api.request.GetCommonFriendsRequest;
import com.example.friendmanagement.api.response.CreateOneFriendResponse;
import com.example.friendmanagement.api.response.GetAllFriendsResponse;
import com.example.friendmanagement.api.response.GetCommonFriendsResponse;
import com.example.friendmanagement.error.CreateOneFriendException;
import com.example.friendmanagement.error.GetAllFriendsException;
import com.example.friendmanagement.error.GetCommonFriendsException;
import com.example.friendmanagement.model.FriendEntity;
import com.example.friendmanagement.repository.IBlockerRepository;
import com.example.friendmanagement.repository.IFriendRepository;
import com.example.friendmanagement.service.iService.IFriendService;
import com.example.friendmanagement.util.Constant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FriendServiceImpl implements IFriendService {
    @Autowired
    private IFriendRepository iFriendRepository;

    @Autowired
    private IBlockerRepository iBlockerRepository;

    @Override
    public Mono<CreateOneFriendResponse> createOneFriend(CreateOneFriendRequest createOneFriendRequest) {
        return Mono.just(createOneFriendRequest)
                .filter(request -> Objects.nonNull(request) && Objects.nonNull(request.getFriends()) && request.getFriends().length == 2 && !createOneFriendRequest.getFriends()[0].equals(createOneFriendRequest.getFriends()[1]))
                .switchIfEmpty(Mono.error(new CreateOneFriendException(new Throwable(Constant.REQUEST_BODY_IS_INVALID))))
                .flatMap(request -> iBlockerRepository.countEmailBlockEachOther(request.getFriends()[0], request.getFriends()[1])
                        .filter(numOfEmailBlockEachOther -> numOfEmailBlockEachOther == 0)
                        .flatMap(numOfEmailBlockEachOther -> Mono.just(request))
                        .switchIfEmpty(Mono.error(new CreateOneFriendException(new Throwable(Constant.EMAILS_ARE_BLOCKED_EACH_OTHER))))
                        //.switchIfEmpty(Mono.empty())
                )
                .flatMap(request -> {
                    if (request.getFriends()[0].compareTo(request.getFriends()[1]) > 0) Collections.swap(Arrays.asList(request.getFriends()), 0, 1);
                    return iFriendRepository.save(FriendEntity.builder().email1(request.getFriends()[0]).email2(request.getFriends()[1]).build());
                })
                .map(createdFriendEntity -> CreateOneFriendResponse.builder().success(true).build())
                //.doOnError(e -> log.error("can't create one, reason " + e.getCause()))
                .onErrorMap(e -> new CreateOneFriendException(e.getCause()))
                .onErrorResume(e -> Mono.just(CreateOneFriendResponse.builder().success(false).build()));
    }

    @Override
    public Mono<GetAllFriendsResponse> getAllFriendsByEmail(@RequestBody @NonNull GetAllFriendsRequest getAllFriendsRequest) {
        return Mono.just(getAllFriendsRequest)
                .filter(request -> Objects.nonNull(request) && Objects.nonNull(request.getEmail()))
                .switchIfEmpty(Mono.error(new GetAllFriendsException(new Throwable(Constant.REQUEST_BODY_IS_INVALID))))
                .map(GetAllFriendsRequest::getEmail)
                .flatMap(inputEmail -> {
                    Flux<String> email1Entities = iFriendRepository.findEmail1ByEmail2(inputEmail);
                    Flux<String> email2Entities = iFriendRepository.findEmail2ByEmail1(inputEmail);
                    return email1Entities.mergeWith(email2Entities).collectList();
                })
                .map(outputEmails -> GetAllFriendsResponse.builder().success(true).friends(outputEmails).count(outputEmails.size()).build())
                .onErrorResume(e -> Mono.just(GetAllFriendsResponse.builder().success(false).friends(Collections.emptyList()).count(0).build()));
    }

    @Override
    public Mono<GetCommonFriendsResponse> getCommonFriends(GetCommonFriendsRequest getCommonFriendsRequest) {
        return Mono.just(getCommonFriendsRequest)
                .filter(request -> Objects.nonNull(request) && Objects.nonNull(request.getFriends()) && request.getFriends().length == 2)
                .switchIfEmpty(Mono.error(new GetCommonFriendsException(new Throwable(Constant.REQUEST_BODY_IS_INVALID))))
                .flatMap(request -> iFriendRepository.findCommonEmail(request.getFriends()[0], request.getFriends()[1]).collectList())
                .map(outputEmails -> GetCommonFriendsResponse.builder().success(true).friends(outputEmails).count(outputEmails.size()).build())
                .onErrorMap(e -> new GetCommonFriendsException(e.getCause()))
                .onErrorResume(e -> Mono.just(GetCommonFriendsResponse.builder().success(false).friends(Collections.emptyList()).count(0).build()))
                .switchIfEmpty(Mono.just(GetCommonFriendsResponse.builder().success(false).friends(Collections.emptyList()).count(0).build()));
    }
}
