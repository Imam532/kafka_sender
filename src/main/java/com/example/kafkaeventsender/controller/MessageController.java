package com.example.kafkaeventsender.controller;

import com.example.kafkaeventsender.client.EventClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inuigaming.inuieventstarter.JsonConverter;
import com.inuigaming.inuieventstarter.convertors.KafkaMessageCreator;
import com.inuigaming.inuieventstarter.kafka.MessageService;
import inui.models.blog.rq.RequestById;
import inui.models.constants.Events;
import inui.models.kafka.KafkaMessage;
import inui.models.statistic.Match;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class MessageController {
//    private final KafkaEventSender eventSender;
    private final EventClient eventClient;
    private final MessageService messageService;


    @SneakyThrows
    @GetMapping
    public String matchStart() {
        String s = IOUtils.toString(requireNonNull(getSystemResourceAsStream("match_for_start.json")), UTF_8);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(Events.NEW_MATCH_START, "userId"));
        messageService.sendMessage(kafkaMessage, "inui-gamestats-service");
        return "ok";
    }

    @GetMapping("/event")
    private String sendGameEvents() {
        eventClient.sendMessages();
        return "ok";
    }

    @SneakyThrows
    @GetMapping("/v1/{event}")
    private String send3(@PathVariable String event, @RequestParam("userId") String userId) {
        RequestById body = new RequestById();
        body.setId(userId);
        String s = new ObjectMapper().writeValueAsString(body);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(event, userId));
        messageService.sendMessage(kafkaMessage, "inui-gamestats-service");
        return "ok";
    }

    @GetMapping("/match/{event}")
    private String send4(@PathVariable String event, @RequestParam("matchId") String matchId) throws JsonProcessingException {
        RequestById body = new RequestById();
        body.setId(matchId);
        String s = new ObjectMapper().writeValueAsString(body);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(event, "userId"));
        messageService.sendMessage(kafkaMessage, "inui-gamestats-service");
        return "ok";
    }

    private Map<String, String> getHeaders(String event, String userId) {
        return Map.of(
                "X-APPLICATION-NAME", "inui-api-gateway",
                "X-TIMESTAMP", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "X-STATUS", "SUCCESS",
                "X-USER-ID", userId,
                "X-EVENT-NAME", event,
                "X-TRACE-ID", "a3043784-7334-4091-98ae-cbc2a2c131f5");
    }
}
