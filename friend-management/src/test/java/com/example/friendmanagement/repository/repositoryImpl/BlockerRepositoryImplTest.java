package com.example.friendmanagement.repository.repositoryImpl;

import com.example.friendmanagement.repository.IBlockerRepository;
import com.example.friendmanagement.repository.iRepository.IBlockerRepositoryTest;
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
public class BlockerRepositoryImplTest implements IBlockerRepositoryTest {
    @Autowired
    private IBlockerRepository iBlockerRepository;

    @Test
    @Override
    public void testCountEmailBlockEachOther_Success() {
        Publisher<Integer> setup = iBlockerRepository.countEmailBlockEachOther("u1", "u2").then(Mono.just(0));
        StepVerifier.create(setup).consumeNextWith(response -> Assertions.assertEquals(0, response)).verifyComplete();
    }

    @Test
    @Override
    public void testFindRequestorByTarget_Success() {
        Publisher<List<String>> setup = iBlockerRepository.findRequestorByTarget("u1").then(Mono.just(Arrays.asList("u2", "u3")));
        StepVerifier.create(setup).consumeNextWith(response -> Assertions.assertEquals(Arrays.asList("u2", "u3"), response)).verifyComplete();
    }

    @Test
    @Override
    public void testFindTargetByRequestor_Success() {
        Publisher<List<String>> setup = iBlockerRepository.findTargetByRequestor("u1").then(Mono.just(Arrays.asList("u2", "u3")));
        StepVerifier.create(setup).consumeNextWith(response -> Assertions.assertEquals(Arrays.asList("u2", "u3"), response)).verifyComplete();
    }
}
