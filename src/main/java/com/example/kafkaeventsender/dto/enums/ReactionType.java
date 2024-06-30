package com.example.kafkaeventsender.dto.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum ReactionType {
    LIKE("like"),
    DISLIKE("dislike");

    public final String action;
    public static ReactionType getReactionFromValue(String value){
        return Arrays.stream(ReactionType.values())
                .filter(action -> action.action.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow();
    }
}
