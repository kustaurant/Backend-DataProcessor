package com.kustaurant.dataprocessor.aianalysis;

import com.kustaurant.dataprocessor.aianalysis.dto.AiAnalysisRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiAnalysisOrchestrator {

    private final ReviewCrawler reviewCrawler;

    public void execute(AiAnalysisRequest req) {
        // 리뷰 크롤링
        List<String> reviews = reviewCrawler.crawlReviews(req.url());
        // AI 전처리

    }
}
