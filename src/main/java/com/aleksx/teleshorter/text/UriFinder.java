package com.aleksx.teleshorter.text;

import reactor.core.publisher.Flux;

import java.net.URI;

public interface UriFinder {

    Flux<URI> findUriInText(String text);
}
