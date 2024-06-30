package com.example.kafkaeventsender.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum EventType {
    MVP("round_mvp"),
    //match event
    SERIES_END("series_end"), //+
    MATCH_END("map_result"), //+
    SERIES_START("series_start"), //+,
    MATCH_PAUSE("match_paused"), //+
    MATCH_UNPAUSE("match_unpause"), //+
    MATCH_PAUSE_BEGAN("pause_began"), //+
    KNIFE_ROUND("knife_start"), //+
    KNIFE_ROUND_WON("knife_won"), //+
    MATCH_GOING_LIVE("going_live"), //+
    START_HALFTIME("start_halftime"), //+
    ROUND_START("round_start"), //+
    ROUND_END("round_end"), //+
    PLAYER_CONNECTED("player_connected"), //+
    PLAYER_DISCONNECTED("player_disconnected"), //+
    //game event
    PLAYER_DEATH("player_death"), //+
    PLAYER_BLIND("player_blind"), //+
    PLAYER_HURT("player_hurt"), //+
    WEAPON_FIRE("weapon_fire"), //+
    BOMB_PLANTED("bomb_planted"), //+
    BOMB_DEFUSED("bomb_defused"), //+
    BOMB_EXPLODED("bomb_exploded"), //+
    TEAMMATE_KILL("teammate_kill"),
    SUICIDE("suicide"),
    UNKNOWN("unknown");

    @JsonValue
    public final String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    @JsonCreator
    public static EventType getEventTypeByValue(String value){
        return Arrays.stream(EventType.values())
                .filter(eventType -> eventType.eventName.equalsIgnoreCase(value))
                .findFirst()
                .orElse(EventType.UNKNOWN);
    }

    public static EventType fromStringName(String statusName) {
        for (EventType eventType : EventType.values()) {
            if (eventType.name().equalsIgnoreCase(statusName)) {
                return eventType;
            }
        }
        return UNKNOWN;
    }
}
