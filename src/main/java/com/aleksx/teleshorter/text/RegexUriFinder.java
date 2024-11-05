package com.aleksx.teleshorter.text;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RegexUriFinder implements UriFinder {


    @Override
    public Flux<URI> findUriInText(String text) {
        List<URI> foundUrls = new ArrayList<>();
        var matcher = Patterns.WEB_URL.matcher(text);
        while (matcher.find()) {
            var foundInText = matcher.group();
            try {
                foundUrls.add(URI.create(foundInText));
            } catch (IllegalArgumentException e) {
                log.warn("Broken uri filtered - {}", foundInText);
            }

        }

        return Flux.fromIterable(foundUrls);
    }
}
