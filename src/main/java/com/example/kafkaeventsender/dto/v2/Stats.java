package com.example.kafkaeventsender.dto.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Stats {
    private int moneySpent;
    private int moneyEarned;
    private int kills;
    private int deaths;
    private int assists;
    private int jumps;
    private int killedChicken;
    private int brokenWindows;
    private int backstabsKnife;
    private int chatMessages;
    private double kast;
    private Clutches clutches;
}
