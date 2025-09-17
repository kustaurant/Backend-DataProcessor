package com.kustaurant.dataprocessor.infrastructure.messaging.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @param aiAnalysisStart - AI 전처리 파이프라인을 시작하는 메시지 토픽(key)
 * @param aiAnalysisDlq - AI 전처리 파이프라인 시작에 대한 DLQ 토픽(key)
 * @param group - 현재 프로세스의 컨슈머 그룹명
 */
@ConfigurationProperties(prefix = "streams")
public record RedisStreamsProps (
        String aiAnalysisStart,
        String aiAnalysisDlq,
        String group
) {

}
