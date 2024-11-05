package com.aleksx.teleshorter.shorter.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Document(value = "ShortUrl")
public record ShortUrlEntity(
        @Id String id,
        String rUrl,
        String sUrl,
        boolean isActive,
        long clickCount,
        Instant activeDate) {
}
