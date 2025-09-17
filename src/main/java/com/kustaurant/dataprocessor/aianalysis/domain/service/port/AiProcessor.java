package com.kustaurant.dataprocessor.aianalysis.domain.service.port;

import com.kustaurant.dataprocessor.aianalysis.domain.model.Review;
import com.kustaurant.dataprocessor.aianalysis.domain.model.ReviewAnalysis;
import java.util.List;

public interface AiProcessor {

    ReviewAnalysis analyzeReview(Review review, List<String> situations);
}
