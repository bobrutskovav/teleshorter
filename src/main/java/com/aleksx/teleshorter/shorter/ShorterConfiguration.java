package com.aleksx.teleshorter.shorter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories
public class ShorterConfiguration {


    @Bean
    public ShorterLink shorterLink(ShortLinkRepository shortLinkRepository,
                                   TeleshorterProps teleshorterProps,
                                   ReactiveMongoTemplate reactiveMongoTemplate) {
        return new DefaultShorterLink(shortLinkRepository, reactiveMongoTemplate, teleshorterProps);
    }
}
