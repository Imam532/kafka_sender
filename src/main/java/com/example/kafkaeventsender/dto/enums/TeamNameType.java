package com.example.kafkaeventsender.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TeamNameType {
    RED("red"),
    BLUE("blue"),
    NONE("none"),
    DRAW("unknown");

    @JsonValue
    public final String winner;

    TeamNameType(String winner) {
        this.winner = winner;
    }

    @JsonCreator
    public static TeamNameType getEventTypeByValue(String value) {
        return Arrays.stream(TeamNameType.values())
                .filter(type -> type.winner.equalsIgnoreCase(value))
                .findFirst()
                .orElse(TeamNameType.NONE);
    }
}
