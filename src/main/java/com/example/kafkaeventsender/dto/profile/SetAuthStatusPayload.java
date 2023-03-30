package com.example.kafkaeventsender.dto.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SetAuthStatusPayload(@JsonProperty(required = true)String authStatus) {
}
