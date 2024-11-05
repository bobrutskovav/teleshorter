package com.aleksx.teleshorter.shorter.dto;

import java.net.URI;
import java.time.OffsetDateTime;

public record ShortUrlDto(String id,
                          URI rUrl,
                          URI sUrl,
                          boolean isActive,
                          OffsetDateTime activeDate) {

}
