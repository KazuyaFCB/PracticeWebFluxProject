package com.example.friendmanagement.service;

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
import com.example.friendmanagement.repository.FriendRepository;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class FriendService implements IFriendService {
    @Autowired
    private FriendRepository friendRepository;

    public Flux<FriendEntity> getAll() {
        return Flux.just(new FriendEntity("u1@example.com","u2@example.com") , new FriendEntity("u3@example.com", "u1@example.com"));
    }

    @Override
    public Mono<CreateOneFriendResponse> createOneFriend(CreateOneFriendRequest createOneFriendRequest) {
        return Mono.just(createOneFriendRequest)
                .filter(request -> Objects.nonNull(request) && Objects.nonNull(request.getFriends()) && request.getFriends().length == 2 && !createOneFriendRequest.getFriends()[0].equals(createOneFriendRequest.getFriends()[1]))
                .flatMap(request -> {
                    if (request.getFriends()[0].compareTo(request.getFriends()[1]) > 0) Collections.swap(Arrays.asList(request.getFriends()), 0, 1);
                    return friendRepository.save(FriendEntity.builder().email1(request.getFriends()[0]).email2(request.getFriends()[1]).build());
                })
                .map(createdFriendEntity -> CreateOneFriendResponse.builder().success(true).build())
                //.doOnError(e -> log.error("can't create one, reason " + e.getCause()))
                .onErrorMap(e -> new CreateOneFriendException(e))
                .onErrorResume(e -> Mono.just(CreateOneFriendResponse.builder().success(false).build()))
                .switchIfEmpty(Mono.just(CreateOneFriendResponse.builder().success(false).build()));

//        CreateOneFriendResponse createOneFriendResponse = new CreateOneFriendResponse();
//        try {
//            if (createOneFriendRequest == null || createOneFriendRequest.getFriends() == null || createOneFriendRequest.getFriends().length > 2 || createOneFriendRequest.getFriends()[0].equals(createOneFriendRequest.getFriends()[1])) {
//                createOneFriendResponse.setSuccess(false);
//                return Mono.just(createOneFriendResponse);
//            }
//            String email1, email2;
//            if (createOneFriendRequest.getFriends()[0].compareTo(createOneFriendRequest.getFriends()[1]) < 0) {
//                email1 = createOneFriendRequest.getFriends()[0];
//                email2 = createOneFriendRequest.getFriends()[1];
//            } else {
//                email1 = createOneFriendRequest.getFriends()[1];
//                email2 = createOneFriendRequest.getFriends()[0];
//            }
//            Mono<FriendEntity> friendEntityAsMono = friendRepository.save(FriendEntity.builder().email1(email1).email2(email2).build());
//            FriendEntity friendEntity = friendEntityAsMono.block();
//            createOneFriendResponse.setSuccess(true);
//        } catch (Exception e) {
//            createOneFriendResponse.setSuccess(false);
//        }
//        return Mono.just(createOneFriendResponse);
    }

    @Override
    public Mono<GetAllFriendsResponse> getAllFriendsByEmail(@RequestBody @NonNull GetAllFriendsRequest getAllFriendsRequest) {
        return Mono.just(getAllFriendsRequest)
                .filter(request -> Objects.nonNull(request) && Objects.nonNull(request.getEmail()))
                .map(GetAllFriendsRequest::getEmail)
                .flatMap(inputEmail -> {
                    Flux<String> email1Entities = friendRepository.findEmail1ByEmail2(inputEmail);
                    Flux<String> email2Entities = friendRepository.findEmail2ByEmail1(inputEmail);
                    return email1Entities.mergeWith(email2Entities).collectList();
                })
                //.flatMap(x -> sleep10(x))
                .map(outputEmails -> GetAllFriendsResponse.builder().success(true).friends(outputEmails).count(outputEmails.size()).build())
                .doOnError(e -> log.error("can't get all by email, reason " + e.getCause()))
                .onErrorMap(e -> new GetAllFriendsException())
                .onErrorResume(e -> Mono.just(GetAllFriendsResponse.builder().success(false).friends(Collections.emptyList()).count(0).build()))
                .switchIfEmpty(Mono.just(GetAllFriendsResponse.builder().success(false).friends(Collections.emptyList()).count(0).build()));

//        GetAllFriendsResponse getAllFriendsResponse = new GetAllFriendsResponse();
//        try {
//            if (getAllFriendsRequest == null || getAllFriendsRequest.getEmail() == null) {
//                getAllFriendsResponse.setFriends(null);
//                getAllFriendsResponse.setCount(0);
//                getAllFriendsResponse.setSuccess(false);
//            } else {
//                Thread.sleep(5000);
//                Flux<String> emailEntitiesAsFlux = friendService.getAllByEmail(getAllFriendsRequest.getEmail());
//                List<String> emailEntities = emailEntitiesAsFlux.collectList().block();
//                getAllFriendsResponse.setFriends(emailEntities);
//                getAllFriendsResponse.setCount(emailEntities.size());
//                getAllFriendsResponse.setSuccess(true);
//            }
//        } catch (Exception e) {
//            getAllFriendsResponse.setFriends(null);
//            getAllFriendsResponse.setCount(0);
//            getAllFriendsResponse.setSuccess(false);
//        }
//        return Mono.just(getAllFriendsResponse);
    }

    @Override
    public Mono<GetCommonFriendsResponse> getCommonFriends(GetCommonFriendsRequest getCommonFriendsRequest) {
        return Mono.just(getCommonFriendsRequest)
                .filter(request -> Objects.nonNull(request) && Objects.nonNull(request.getFriends()) && request.getFriends().length == 2)
                .flatMap(request -> friendRepository.findCommonEmail(request.getFriends()[0], request.getFriends()[1]).collectList())
                .map(outputEmails -> GetCommonFriendsResponse.builder().success(true).friends(outputEmails).count(outputEmails.size()).build())
                .doOnError(e -> log.error("can't get common friends, reason " + e.getCause()))
                .onErrorMap(e -> new GetCommonFriendsException())
                .onErrorResume(e -> Mono.just(GetCommonFriendsResponse.builder().success(false).friends(Collections.emptyList()).count(0).build()))
                .switchIfEmpty(Mono.just(GetCommonFriendsResponse.builder().success(false).friends(Collections.emptyList()).count(0).build()));

//        GetCommonFriendsResponse getCommonFriendsResponse = new GetCommonFriendsResponse();
//        try {
//            if (getCommonFriendsRequest == null || getCommonFriendsRequest.getFriends() == null || getCommonFriendsRequest.getFriends().length > 2) {
//                getCommonFriendsResponse.setFriends(null);
//                getCommonFriendsResponse.setCount(0);
//                getCommonFriendsResponse.setSuccess(false);
//            } else {
//                Flux<String> emailEntitiesAsFlux = friendService.getCommon(getCommonFriendsRequest.getFriends()[0], getCommonFriendsRequest.getFriends()[1]);
//                List<String> emailEntities = emailEntitiesAsFlux.collectList().block();
//                getCommonFriendsResponse.setFriends(emailEntities);
//                getCommonFriendsResponse.setCount(emailEntities.size());
//                getCommonFriendsResponse.setSuccess(true);
//            }
//        } catch (Exception e) {
//            getCommonFriendsResponse.setFriends(null);
//            getCommonFriendsResponse.setCount(0);
//            getCommonFriendsResponse.setSuccess(false);
//        }
//        return Mono.just(getCommonFriendsResponse);
    }
    public Mono<List<String>> sleep10(List<String> x) {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {

        }
        return Mono.just(x);
    }
}
