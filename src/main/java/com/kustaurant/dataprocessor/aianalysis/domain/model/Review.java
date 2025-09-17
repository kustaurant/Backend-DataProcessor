package com.kustaurant.dataprocessor.aianalysis.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review {

    private static final int MIN_REVIEW_LENGTH = 10;

    private String body;

    public static Review of(String body) {
        if (!isValid(body)) {
            throw new IllegalArgumentException("Invalid review");
        }
        return new Review(refineBody(body));
    }

    public static boolean isValid(String body) {
        return refineBody(body).length() > MIN_REVIEW_LENGTH;
    }

    private static String refineBody(String body) {
        return body.replaceAll("\\s+", " ").trim();
    }
}
