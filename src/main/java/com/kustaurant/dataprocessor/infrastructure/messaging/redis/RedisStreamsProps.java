package com.kustaurant.dataprocessor.infrastructure.messaging.redis;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @param topics - 토픽(streams key) 리스트
 * @param group - 현재 프로세스의 컨슈머 그룹명
 */
@ConfigurationProperties(prefix = "streams")
public record RedisStreamsProps (
        List<String> topics,
        String group
) {

}
