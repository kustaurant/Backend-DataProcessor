package com.kustaurant.dataprocessor.infrastructure.messaging.redis;

import static org.assertj.core.api.Assertions.*;

import com.kustaurant.dataprocessor.global.util.JsonUtils;
import com.kustaurant.dataprocessor.infrastructure.messaging.MessagePublisher;
import com.kustaurant.dataprocessor.infrastructure.messaging.MessageSubscriber;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestRedisConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class StreamsE2ETest {

    private static final List<String> TEST_TOPICS = List.of(
            "test-topic1-" + UUID.randomUUID(),
            "test-topic2-" + UUID.randomUUID()
    );
    private static final String TEST_GROUP = "test-group-" + UUID.randomUUID();

    @Autowired
    private MessagePublisher<String> publisher;
    @Autowired
    private MessageSubscriber subscriber;

    @DynamicPropertySource
    static void redisProps(DynamicPropertyRegistry r) {
        r.add("streams.topics", () -> String.join(",", TEST_TOPICS));
        r.add("streams.group", () -> TEST_GROUP);
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
        subscriber.subscribe(TEST_TOPICS.getFirst(), TEST_GROUP, consumer, TestDto.class, req -> {
            received.set(req);
            latch.countDown();
        });
        publisher.publish(TEST_TOPICS.getFirst(), JsonUtils.serialize(message));


        // then
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(received.get()).isEqualTo(message);
    }

    record TestDto(
            long id,
            String payload
    ) {}
}