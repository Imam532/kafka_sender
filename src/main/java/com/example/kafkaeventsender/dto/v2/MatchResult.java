package com.example.kafkaeventsender.dto.v2;

import com.example.kafkaeventsender.dto.enums.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MatchResult {
    private Team red;
    private Team blue;
    private int mapNumber;
    private TeamNameType winnerMap;
}
