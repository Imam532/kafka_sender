//package com.example.kafkaeventsender.kafka;
//
//import inui.models.kafka.Headers;
//import inui.models.kafka.KafkaMessage;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class KafkaConsumer {
//
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
//
//
////    @KafkaListener(topics = "${spring.kafka.consumer.topic}", containerFactory = "eventKafkaListenerContainerFactory")
//    public void receive(KafkaMessage message) {
//        LOGGER.info("[traceId = {}]Received  message {}", message, message.getHeaders().get(Headers.X_TRACE_ID));
//    }
//
//}
