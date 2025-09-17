package com.kustaurant.dataprocessor.infrastructure.messaging.redis;

import java.time.Duration;
import org.springframework.boot.autoconfigure.data.redis.RedisConnectionDetails;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@TestConfiguration(proxyBeanMethods = false)
public class TestRedisConfig {

    @Bean
    @ServiceConnection(name = "redis", type = RedisConnectionDetails.class)
    GenericContainer<?> redis() {
        return new GenericContainer<>("redis:7-alpine")
                .withExposedPorts(6379)
                .waitingFor(Wait.forListeningPort());
    }

    @Bean
    @Primary
    StreamMessageListenerContainerOptions<String, MapRecord<String,String,String>> testStreamOptions() {
        return StreamMessageListenerContainerOptions.builder()
                .pollTimeout(Duration.ofMillis(200)) // 테스트용 짧은 poll
                .build();
    }
}
