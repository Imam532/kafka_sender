package com.example.kafkaeventsender.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Side {
    T("t"),
    CT("ct");

    @JsonValue
    public final String value;

    Side(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Side getEventTypeByValue(String value){
        return Arrays.stream(Side.values())
                .filter(side -> side.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown side value: " + value));
    }
}
