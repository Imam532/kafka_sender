package com.example.kafkaeventsender.client;

import com.example.kafkaeventsender.dto.analytics.EventMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.inuigaming.inuieventstarter.JsonConverter;
import com.inuigaming.inuieventstarter.convertors.KafkaMessageCreator;
import com.inuigaming.inuieventstarter.kafka.MessageService;
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

    public void sendMessages() {
        webClient
                .post()
                .uri("/api/v1/statistics")
                .body(getMessage(), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(System.out::println);
    }

    private Flux<EventMessage> getMessage() {
        try {
            String s = IOUtils.toString(
                    requireNonNull(getSystemResourceAsStream("match_log.json")),
                    UTF_8);
            List<EventMessage> eventMessages = JsonConverter.objectFromJson(s, new TypeReference<List<EventMessage>>() {
            });
            Collections.reverse(eventMessages);
            return Flux.fromIterable(eventMessages);
        } catch (Exception e) {
            return Flux.empty();
        }
    }

}
