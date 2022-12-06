package com.example.friendmanagement.repository.repositoryImpl;

import com.example.friendmanagement.repository.IFriendRepository;
import com.example.friendmanagement.repository.iRepository.IFriendRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@DataR2dbcTest
@ExtendWith(SpringExtension.class)
public class FriendRepositoryImplTest implements IFriendRepositoryTest {
    @Autowired
    private IFriendRepository iFriendRepository;

    @Test
    @Override
    public void testFindEmail2ByEmail1_Success() {
        Publisher<List<String>> setup = iFriendRepository.findEmail2ByEmail1("u1").then(Mono.just(Arrays.asList("u2", "u3")));
        StepVerifier.create(setup).consumeNextWith(response -> Assertions.assertEquals(Arrays.asList("u2", "u3"), response)).verifyComplete();
    }

    @Test
    @Override
    public void testFindEmail1ByEmail2_Success() {
        Publisher<List<String>> setup = iFriendRepository.findEmail1ByEmail2("u1").then(Mono.just(Arrays.asList("u2", "u3")));
        StepVerifier.create(setup).consumeNextWith(response -> Assertions.assertEquals(Arrays.asList("u2", "u3"), response)).verifyComplete();
    }

    @Test
    @Override
    public void testFindCommonEmail_Success() {
        Publisher<List<String>> setup = iFriendRepository.findCommonEmail("u1", "u2").then(Mono.just(Arrays.asList("u3", "u4")));
        StepVerifier.create(setup).consumeNextWith(response -> Assertions.assertEquals(Arrays.asList("u3", "u4"), response)).verifyComplete();
    }
}
