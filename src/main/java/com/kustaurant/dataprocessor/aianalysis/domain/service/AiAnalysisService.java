package com.kustaurant.dataprocessor.aianalysis.domain.service;

import com.kustaurant.dataprocessor.aianalysis.domain.model.RestaurantAnalysis;
import com.kustaurant.dataprocessor.aianalysis.domain.model.Review;
import com.kustaurant.dataprocessor.aianalysis.domain.model.ReviewAnalysis;
import com.kustaurant.dataprocessor.aianalysis.domain.service.port.AiProcessor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiAnalysisService {

    private final AiProcessor aiProcessor;

    public RestaurantAnalysis analyzeReviews(List<Review> reviews, List<String> situations) {
        // 각 리뷰 AI 분석
        List<ReviewAnalysis> list = reviews.stream()
                .map(review -> aiProcessor.analyzeReview(review, situations))
                .toList();

        // 식당 총평

    }
}
