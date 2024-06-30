package com.example.kafkaeventsender.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ReasonType {
    T_WIN(9),
    CT_WIN(8),
    UNKNOWN(0),
    BOMB_TYPE(7),
    T_ESCAPED(4),
    T_PLANNED(19),
    ROUND_DRAW(10),
    T_SURRENDER(17),
    CT_SURRENDER(18),
    TARGET_BOMBED(1),
    TARGET_SAVED(12),
    SURVIVAL_WIN(21),
    T_NOT_ESCAPED(14),
    SURVIVAL_DRAW(22),
    GAME_COMMENCING(16),
    CT_PREVENT_ESCAPE(5),
    CT_REACHED_HOSTAGE(20),
    ALL_HOSTAGE_RESCUED(11),
    HOSTAGE_NOT_RESCUED(13),
    ESCAPING_T_NEUTRALIZED(6),
        ;

    @JsonValue
    public final int value;

    ReasonType(int value) {
        this.value = value;
    }

    @JsonCreator
    public static ReasonType getReasonTypeByValue(int value) {
        return Arrays.stream(ReasonType.values())
                .filter(reason -> reason.value == value)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
