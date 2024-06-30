package com.example.kafkaeventsender.dto.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import inui.models.statistic.enums.CSWeapons;
import inui.models.statistic.enums.HitGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GameEvent extends CSEvent{
    private int reason;
    private int revenge;
    private int mapNumber;
    private int dominated;
    private int roundNumber;
    private double distance;
    private double blindDuration;
    private boolean noScope;
    private boolean headshot;
    private boolean silenced;
    private boolean thruSmoke;
    private boolean attackerInair;
    private boolean attackerBlind;
    private Player victim;
    private Player assist;
    private Player attacker;
    private CSWeapons weapon;
    private HitGroup hitGroup;
    private WeaponInfo weaponInfo;
}
