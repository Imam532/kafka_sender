package com.example.kafkaeventsender.dto.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetFriendsPayload {
    @JsonProperty("publicName")
    private String publicName;
    @JsonProperty("selfConfirm")
    private boolean selfConfirm;
    @JsonProperty("friendConfirm")
    private boolean friendConfirm;
    @JsonProperty("page")
    Integer page;
    private @JsonProperty("size")
    Integer pageSize;
    @JsonProperty("search")
    private String search;
}
