package com.example.kafkaeventsender.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import inui.models.statistic.Player;
import inui.models.statistic.PlayerScore;
import inui.models.statistic.enums.*;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class GameEventDto {
        @JsonProperty("matchID")
        private String matchId;
        private int tick;
        private StatEventType type;
        private String phase;
        private String weapon;
        private Player victim;
        private Player assister;
        private Player attacker;
        @JsonProperty("dmg_health")
        private Integer dmgHealth;
        @JsonProperty("dmg_armor")
        private Integer dmgArmor;
        private Double duration;
        private WinnerType winner;
        private HitGroup hitgroup;
        private Boolean noscope;
        private Boolean thrusmoke;
        private Boolean headshot;
        private Boolean penetrated;
        private Boolean attackerblind;
        private Boolean assistedflash;
        private Integer round;
        private Integer scoreT;
        private Integer scoreCT;
        private MatchResult status;
        private Map<String, List<String>> lineups;
        @JsonProperty("player_scores")
        private List<PlayerScore> playerScores;

    }

