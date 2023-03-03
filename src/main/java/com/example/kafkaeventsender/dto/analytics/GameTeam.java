package com.example.kafkaeventsender.dto.analytics;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;

public enum GameTeam {
    RED("team1"), BLUE("team2");

    @JsonValue
    public final String team;

    GameTeam(String team) {
        this.team = team;
    }

    @JsonCreator
    public static GameTeam forValue(String team) {
        return teamType(team).orElseThrow(() -> new IllegalArgumentException("Unknown team value: " + team));
    }

    public static Optional<GameTeam> teamType(String value) {

        for (GameTeam gameTeam : values()) {
            if (gameTeam.team.equals(value)) {
                return Optional.of(gameTeam);
            }
        }

        return Optional.empty();
    }
}
