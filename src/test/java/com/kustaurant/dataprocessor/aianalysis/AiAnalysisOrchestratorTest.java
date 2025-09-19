package com.kustaurant.dataprocessor.aianalysis;

import static org.junit.jupiter.api.Assertions.*;

import com.kustaurant.dataprocessor.aianalysis.infrastructure.messaging.dto.AiAnalysisRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiAnalysisOrchestratorTest {

    @Autowired
    private AiAnalysisOrchestrator aiAnalysisOrchestrator;

    @Test
    void test() {
//        aiAnalysisOrchestrator.execute(
//                new AiAnalysisRequest(
//                        "",
//                        "https://map.naver.com/p/entry/place/1447839643",
//                        List.of("혼밥", "2~3인", "4인 이상", "친구 초대", "소개팅", "단체 회식", "배달", "야식")
//                )
//        );
    }
}