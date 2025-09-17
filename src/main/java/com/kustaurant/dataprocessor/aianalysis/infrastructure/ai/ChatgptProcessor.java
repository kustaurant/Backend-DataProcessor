package com.kustaurant.dataprocessor.aianalysis.infrastructure.ai;

import com.kustaurant.dataprocessor.aianalysis.domain.model.Review;
import com.kustaurant.dataprocessor.aianalysis.domain.model.ReviewAnalysis;
import com.kustaurant.dataprocessor.aianalysis.domain.service.port.AiProcessor;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatgptProcessor implements AiProcessor {

    @Override
    public ReviewAnalysis analyzeReview(Review review, List<String> situations) {
        return null;
    }
}
