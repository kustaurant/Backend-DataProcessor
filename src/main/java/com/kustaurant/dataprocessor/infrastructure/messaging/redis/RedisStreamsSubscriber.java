package com.kustaurant.dataprocessor.infrastructure.messaging.redis;

import com.kustaurant.dataprocessor.global.util.JsonUtils;
import com.kustaurant.dataprocessor.infrastructure.messaging.MessageSubscriber;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisStreamsSubscriber implements MessageSubscriber {

    private final StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer;
    private final StringRedisTemplate redisTemplate;

    @Override
    public <T> void subscribe(String topic, String group, String consumerName, Class<T> type,
            Consumer<T> handler) {

        // 구독 시작
        listenerContainer.receive(
                org.springframework.data.redis.connection.stream.Consumer.from(group, consumerName),
                StreamOffset.create(topic, ReadOffset.lastConsumed()),
                (MapRecord<String, String, String> message) -> {
                    try {
                        String payload = message.getValue().get("payload");
                        RecordId id = message.getId();
                        log.info("[Stream Id: {}] 메시지 수신됨. 내용: {}", id, payload);

                        // 비즈니스 로직 처리
                        handler.accept(JsonUtils.deserialize(payload, type));

                        // ack
                        redisTemplate.opsForStream().acknowledge(
                                topic, group, id
                        );
                    } catch (Exception e) {
                        log.error("[Stream Id: {}] id crawling 메시지 처리 과정에서 에러 발생", message.getId(), e);
                    }
                }
        );

        listenerContainer.start();
    }
}
