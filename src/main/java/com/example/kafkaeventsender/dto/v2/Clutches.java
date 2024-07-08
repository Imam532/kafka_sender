package com.example.kafkaeventsender.dto.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Clutches {
    @JsonProperty("v_one")
    private int vOne;

    @JsonProperty("v_two")
    private int vTwo;

    @JsonProperty("v_three")
    private int vThree;

    @JsonProperty("v_four")
    private int vFour;

    @JsonProperty("v_five")
    private int vFive;

    @JsonProperty("v_one_win")
    private int vOneWin;

    @JsonProperty("v_two_win")
    private int vTwoWin;

    @JsonProperty("v_three_win")
    private int vThreeWin;

    @JsonProperty("v_four_win")
    private int vFourWin;

    @JsonProperty("v_five_win")
    private int vFiveWin;

    @JsonProperty("v_one_win_percent")
    private double vOneWinPercent;

    @JsonProperty("v_two_win_percent")
    private double vTwoWinPercent;

    @JsonProperty("v_three_win_percent")
    private double vThreeWinPercent;

    @JsonProperty("v_four_win_percent")
    private double vFourWinPercent;

    @JsonProperty("v_five_win_percent")
    private double vFiveWinPercent;
}