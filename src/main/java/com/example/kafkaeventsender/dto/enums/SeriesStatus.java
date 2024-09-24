package com.example.kafkaeventsender.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum SeriesStatus {
    NEW("new", "The match was successfully created"),
    ENDED("ended", "The match was successfully ended"),
    STARTED("started", "The match was successfully started"),
    DRAW("draw", "The match was ended when both team leave"),
    NO_ONE_CONNECTED("no_one_connected", "No one connected on game"),
    NOT_STARTED("not_started", "The teams haven't assembled within the first 5 minutes in the warmup"),
    NOT_ENDED("not_ended", " The match was paused and after the pause no one appeared on the server"),
    RED_LEAVE("red_leave_match", "The red team exits the server and doesn't return within 3 minutes, the red team wins"),
    BLUE_LEAVE("blue_leave_match", "The blue team exits the server and doesn't return within 3 minutes, the red team wins");

    @JsonValue
    public final String value;
    public final String description;

    SeriesStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonCreator
    public static SeriesStatus getEventTypeByValue(String value) {
        return Arrays.stream(SeriesStatus.values())
                .filter(status -> status.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status value: " + value));
    }
}
