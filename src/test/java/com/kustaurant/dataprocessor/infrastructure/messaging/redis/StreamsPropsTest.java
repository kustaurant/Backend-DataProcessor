package com.kustaurant.dataprocessor.infrastructure.messaging.redis;

import com.kustaurant.dataprocessor.aianalysis.messaging.MessagingProps;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class StreamsPropsTest {

    private static final String TEST_TOPIC = "test-topic";
    private static final String TEST_DLT = "test-dead-dlt";
    private static final String GROUP = "test-group";

    @Autowired
    private MessagingProps messagingProps;

    @DynamicPropertySource
    static void redisProps(DynamicPropertyRegistry r) {
        r.add("streams.ai-analysis-start", () -> TEST_TOPIC);
        r.add("streams.ai-analysis-dlq", () -> TEST_DLT);
        r.add("streams.group", () -> GROUP);
    }

    @Test
    @DisplayName("레디스 관련 프로퍼티 정상 세팅 확인")
    void testConfigurationProperties() {
        Assertions.assertThat(messagingProps).isEqualTo(
                new MessagingProps(
                        TEST_TOPIC,
                        TEST_DLT,
                        GROUP
                )
        );
    }
}