package com.aleksx.teleshorter.shorter.api;


import com.aleksx.teleshorter.shorter.ShorterLink;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@AllArgsConstructor
public class TeleshorterHandler {

    private ShorterLink shorterLink;


    public Mono<ServerResponse> getUrl(ServerRequest serverRequest) {
        return shorterLink.getFullUrlById(serverRequest.pathVariable("id"))
                .flatMap(this::redirectTo)
                .switchIfEmpty(notFound());
    }


    private Mono<ServerResponse> redirectTo(URI location) {
        return ServerResponse.status(HttpStatus.FOUND).location(location).build();
    }

    private Mono<ServerResponse> notFound() {
        return ServerResponse.status(HttpStatus.NOT_FOUND).build();
    }
}
