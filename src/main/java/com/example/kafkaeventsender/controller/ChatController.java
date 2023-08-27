package com.example.kafkaeventsender.controller;

import com.inuigaming.inuieventstarter.JsonConverter;
import com.inuigaming.inuieventstarter.kafka.MessageService;
import inui.models.kafka.KafkaMessage;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;

    @SneakyThrows
    @GetMapping("/private-channel")
    public String createPrivateChannel() {
        String s = IOUtils.toString(
                requireNonNull(getSystemResourceAsStream("chat/create_channel.json")),
                UTF_8);
        KafkaMessage kafkaMessage = JsonConverter.objectFromJson(s, KafkaMessage.class);
        messageService.sendMessage(kafkaMessage, "inui-chat-service");
        return HttpStatus.OK.getReasonPhrase();
    }
}
