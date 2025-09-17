package com.kustaurant.dataprocessor.infrastructure.messaging.redis;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class StreamsPropsTest {

    private static final String TOPICS = "test-topic1,test-topic2";
    private static final String GROUP = "test-group";

    @Autowired
    private RedisStreamsProps redisStreamsProps;

    @DynamicPropertySource
    static void redisProps(DynamicPropertyRegistry r) {
        r.add("streams.topics", () -> TOPICS);
        r.add("streams.group", () -> GROUP);
    }

    @Test
    @DisplayName("레디스 관련 프로퍼티 정상 세팅 확인")
    void testConfigurationProperties() {
        Assertions.assertThat(redisStreamsProps).isEqualTo(
                new RedisStreamsProps(
                        List.of("test-topic1", "test-topic2"),
                        GROUP
                )
        );
    }
}