package com.kustaurant.dataprocessor.aianalysis.infrastructure.ai;

import com.kustaurant.dataprocessor.aianalysis.domain.model.Review;
import com.kustaurant.dataprocessor.aianalysis.domain.model.ReviewAnalysis;
import com.kustaurant.dataprocessor.aianalysis.domain.service.port.AiProcessor;
import com.kustaurant.dataprocessor.aianalysis.infrastructure.ai.dto.ScoreResponse;
import com.kustaurant.dataprocessor.aianalysis.infrastructure.ai.dto.SituationsResponse;
import com.kustaurant.dataprocessor.global.util.JsonUtils;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiProcessor implements AiProcessor {

    private final OpenAiChatModel openAiChatModel;

    @Override
    public Optional<ReviewAnalysis> analyzeReview(Review review, List<String> situations) {
        String scoreJson = openAiChatModel.call(
                OpenAiPrompts.getMainScorePrompt(List.of(1, 2, 3, 4, 5), review.body())
        ).getResult().getOutput().getText();
        String situationsJson = openAiChatModel.call(
                OpenAiPrompts.getSituationPrompt(situations, review.body())
        ).getResult().getOutput().getText();

        ScoreResponse scoreRes = JsonUtils.deserialize(scoreJson, ScoreResponse.class);
        SituationsResponse situationsRes = JsonUtils.deserialize(situationsJson, SituationsResponse.class);

        return Optional.of(new ReviewAnalysis(
                review, scoreRes.score(), scoreRes.sentiment(), situationsRes.situations()
        ));
    }
}
