package com.kustaurant.dataprocessor.infrastructure.messaging.redis;

import io.lettuce.core.RedisBusyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.stereotype.Component;

/**
 * 스프링 시작할 때 구독할 Redis Streams가 있는지 확인하고, 없으면 생성합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisStreamsBootstrap implements ApplicationRunner {

    private final RedisConnectionFactory cf;
    private final RedisStreamsProps streamsProps;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ensureGroup(streamsProps.aiAnalysisStart(), streamsProps.group());
        ensureGroup(streamsProps.aiAnalysisDlq(), streamsProps.group());
    }

    private void ensureGroup(String key, String group) {
        var conn = cf.getConnection();
        var sc = conn.streamCommands();
        try {
            sc.xGroupCreate(
                    key.getBytes(),
                    group,
                    ReadOffset.from("0"), // 0: 처음 메시지부터 읽기, $: 연결된 이후 메시지 부터 읽기
                    true // 없을 경우 컨슈머 그룹을 만듦
            );
        } catch (DataAccessException e) {
            if (e.getCause() instanceof RedisBusyException
                    && e.getCause().getMessage().contains("BUSYGROUP")) {
                log.info("key&group 조합이 이미 있습니다. (key-{}, group-{})", key, group);
            } else {
                throw e;
            }
        }
    }
}
