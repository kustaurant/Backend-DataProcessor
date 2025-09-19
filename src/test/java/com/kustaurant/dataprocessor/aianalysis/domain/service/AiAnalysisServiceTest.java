package com.kustaurant.dataprocessor.aianalysis.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import com.kustaurant.dataprocessor.aianalysis.domain.model.Review;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiAnalysisServiceTest {

    @Autowired
    private AiAnalysisService aiAnalysisService;

    @Test
    void test() {
        List<Review> req = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            req.add(new Review("리뷰는 웨이팅이 있었다는 사실만 언급하고 있어 전반적인 만족도는 판단할 수 없으나 긍정적인 또는 부정적인 감정이 뚜렷하지 않음."));
        }
        aiAnalysisService.analyzeReviews(
                req,
                List.of("혼밥", "2~3인", "4인 이상", "친구 초대", "소개팅", "단체 회식", "배달", "야식")
        );
    }
}