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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventClient {

    private final MessageService messageService;
    private final KafkaMessageCreator kafkaMessageCreator;
    //    private final KafkaEventSender sender;
    private final WebClient webClient;

    public void sendMessages(String file) {
        this.getMessage(file);
//        webClient
//                .post()
//                .uri("/api/v1/statistics")
//                .body(getMessage(file), EventMessage.class)
//                .retrieve()
//                .bodyToMono(String.class)
//                .subscribe(System.out::println);
    }

    private void getMessage(String file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String s = IOUtils.toString(
                    requireNonNull(getSystemResourceAsStream(file)),
                    UTF_8);
            var gameEvents = JsonConverter.objectFromJson(s, new TypeReference<List<GameEvent>>() {
            });
            Collections.reverse(gameEvents);
            var messages = gameEvents.stream()
                    .map(gameEvent -> {
                        try {
                            return new EventMessage().setEvent(objectMapper.writeValueAsString(gameEvent));
                        } catch (JsonProcessingException e) {
                            return new EventMessage().setEvent("{}");
                        }
                    })
                    .map(Mono::just)
//                    .map(message -> webClient
//                            .post()
//                            .uri("/api/v1/statistics")
//                            .body(message, EventMessage.class)
//                            .retrieve()
//                            .bodyToMono(String.class)
//                            .subscribe())
                    .toList();
            log.info("resp {}", messages);

            Flux.fromIterable(messages)
//                    .delayElements(Duration.ofMillis(100))
                    .flatMap(message -> webClient
                            .post()
                            .uri("/api/v1/statistics")
                            .body(message, EventMessage.class)
                            .retrieve()
                            .bodyToMono(String.class))
                    .subscribe(tuple2 -> log.info("RESPONSE: {}", tuple2));
        } catch (Exception e) {
            log.error("ERROR:{}", e.getMessage());
        }
    }

}
