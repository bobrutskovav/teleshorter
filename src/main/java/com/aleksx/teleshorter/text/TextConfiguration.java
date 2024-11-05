package com.aleksx.teleshorter.text;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextConfiguration {


    @Bean
    public UriFinder uriFinder() {
        return new RegexUriFinder();
    }
}
