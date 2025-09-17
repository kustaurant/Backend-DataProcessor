package com.kustaurant.dataprocessor.aianalysis.infrastructure.messaging.dto;

import java.util.List;

public record AiAnalysisResponse(
        String naverId,
        double mainScore,
        List<String> situations
) {

}
