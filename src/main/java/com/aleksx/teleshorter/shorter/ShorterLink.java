package com.aleksx.teleshorter.shorter;

import com.aleksx.teleshorter.shorter.dto.ShortUrlDto;
import reactor.core.publisher.Mono;

import java.net.URI;

public interface ShorterLink {


    Mono<ShortUrlDto> shortUri(URI uri);

    Mono<URI> getFullUrlById(String id);

}
