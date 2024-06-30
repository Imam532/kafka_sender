package com.example.kafkaeventsender.dto.v2;

import com.example.kafkaeventsender.dto.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import inui.models.statistic.enums.CSWeapons;
import inui.models.statistic.enums.HitGroup;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CSPluginEvent {
    @JsonProperty("matchid")
    private String matchId;
    private EventType event;
    private int tick;
    private int numMaps;
    private int mapNumber;
    private int roundNumber;
    private int redSeriesScore;
    private int blueSeriesScore;
    private int revenge;
    private int overtime;
    private int dominated;
    private int penetrated;
    private double distance;
    private double blindDuration;
    private boolean swapped;
    private boolean headshot;
    private boolean silenced;
    private boolean teammate;
    private boolean thruSmoke;
    private boolean attackerInair;
    private boolean attackerBlind;
    private Team red;
    private Team blue;
    private Side side;
    private Player player;
    private Player victim;
    private Player assist;
    private Player attacker;
    private CSWeapons weapon;
    private HitGroup hitGroup;
    private ReasonType reason;
    private SeriesStatus status;
    private WeaponInfo weaponInfo;
    private RoundWinner winnerRound;
    private TeamNameType team;
    private TeamNameType winnerMap;
    private TeamNameType winnerSeries;
}
