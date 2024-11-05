package com.aleksx.teleshorter.shorter.api;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@AllArgsConstructor
public class TeleshorterRouter {

    private TeleshorterHandler teleshorterHandler;

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route(GET("/{id}"), teleshorterHandler::getUrl);
    }
}
