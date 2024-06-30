package com.example.kafkaeventsender.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
public class WebClientLoggingFilter implements ExchangeFilterFunction {

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        long startTime = System.currentTimeMillis();
        var requestBodyBuilder = new StringBuilder();
        var responseBodyBuilder = new StringBuilder();
        return next.exchange(writeBody(request, requestBodyBuilder))
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(body -> {
                            responseBodyBuilder.append(body);
                            return clientResponse;
                        }))
                .map(clientResponse -> {
                    var builder = ClientResponse.from(clientResponse);
                    if (StringUtils.isNotBlank(responseBodyBuilder)) {
                        builder.body(responseBodyBuilder.toString());
                    }
                    log.info(logRequest(request, requestBodyBuilder.toString()));
                    log.info(logResponse(clientResponse, responseBodyBuilder.toString(), startTime));
                    return builder.build();
                });
    }


    private static String logRequest(ClientRequest clientRequest, String body) {
        return ("WebClient Request: %s %s.\nHeaders: %s.\nBody: %s").formatted(
                clientRequest.method(),
                clientRequest.url(),
                clientRequest.headers(),
                body);
    }

    private static String logResponse(ClientResponse clientResponse, String body, long startTime) {
        return "WebClient Response: %s (%d ms).\nHeaders: %s.\n Body: %s".formatted(
                clientResponse.statusCode(),
                System.currentTimeMillis() - startTime,
                clientResponse.headers().asHttpHeaders(),
                body);
    }

    private static ClientRequest writeBody(ClientRequest request, StringBuilder stringBuilder) {
        return ClientRequest.from(request)
                .body((outputMessage, context) -> request.body().insert(new ClientHttpRequestDecorator(outputMessage) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        return super.writeWith(Mono.from(body)
                                .doOnNext(dataBuffer -> stringBuilder.append(dataBuffer.toString(StandardCharsets.UTF_8))));
                    }
                }, context))
                .build();
    }
}
