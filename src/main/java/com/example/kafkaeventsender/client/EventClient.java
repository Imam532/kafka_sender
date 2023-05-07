package com.example.kafkaeventsender.client;

import com.example.kafkaeventsender.dto.analytics.EventMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inuigaming.inuieventstarter.JsonConverter;
import com.inuigaming.inuieventstarter.convertors.KafkaMessageCreator;
import com.inuigaming.inuieventstarter.kafka.MessageService;
import inui.models.statistic.GameEvent;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;


@Component
@RequiredArgsConstructor
public class EventClient {

    private final MessageService messageService;
    private final KafkaMessageCreator kafkaMessageCreator;
//    private final KafkaEventSender sender;
    private final WebClient webClient;

    public void sendMessages(String file) {
        webClient
                .post()
                .uri("/api/v1/statistics")
                .body(getMessage(file), EventMessage.class)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(System.out::println);
    }

    private Flux<EventMessage> getMessage(String file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String s = IOUtils.toString(
                    requireNonNull(getSystemResourceAsStream(file)),
                    UTF_8);
            var gameEvents = JsonConverter.objectFromJson(s, new TypeReference<List<GameEvent>>() {
            });
            Collections.reverse(gameEvents);
            return Flux.fromIterable(gameEvents)
                    .map(gameEvent -> {
                        try {
                            return new EventMessage().setEvent(objectMapper.writeValueAsString(gameEvent));
                        } catch (JsonProcessingException e) {
                            return new EventMessage().setEvent("{}");
                        }
                    });
        } catch (Exception e) {
            return Flux.empty();
        }
    }

}
