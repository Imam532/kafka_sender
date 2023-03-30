package com.example.kafkaeventsender.dto.profile;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SetPostsCountPayload(@JsonProperty(required = true) int postsCount) {
}
