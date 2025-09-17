package com.kustaurant.dataprocessor.infrastructure.messaging.redis;

import static org.assertj.core.api.Assertions.*;

import com.kustaurant.dataprocessor.global.util.JsonUtils;
import com.kustaurant.dataprocessor.infrastructure.messaging.MessagePublisher;
import com.kustaurant.dataprocessor.infrastructure.messaging.MessageSubscriber;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestRedisConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class StreamsPubSubTest {

    private static final String TEST_TOPIC = "test-topic-" + UUID.randomUUID();
    private static final String TEST_GROUP = "test-group-" + UUID.randomUUID();

    @Autowired
    private MessagePublisher<String> publisher;
    @Autowired
    private MessageSubscriber subscriber;
    @Autowired
    private RedisStreamsUtils redisStreamsUtils;

    @BeforeEach
    void setUp() {
        redisStreamsUtils.createStreamAndGroupIfNotExists(TEST_TOPIC, TEST_GROUP);
    }

    @Test
    @DisplayName("메시지 발행 및 소비 정상 동작 테스트")
    void streamsPubSubTest() throws InterruptedException {
        // given
        String consumer = UUID.randomUUID().toString();
        TestDto message = new TestDto(1234, "test message");
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<TestDto> received = new AtomicReference<>();

        // when
        subscriber.subscribe(TEST_TOPIC, TEST_GROUP, consumer, TestDto.class, req -> {
            received.set(req);
            latch.countDown();
        });
        publisher.publish(TEST_TOPIC, JsonUtils.serialize(message));


        // then
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(received.get()).isEqualTo(message);
    }

    record TestDto(
            long id,
            String payload
    ) {}
}