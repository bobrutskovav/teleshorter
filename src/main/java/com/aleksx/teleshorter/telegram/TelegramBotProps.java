package com.aleksx.teleshorter.telegram;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("teleshorter.telegram.bot")
@Data
public class TelegramBotProps {
    private String token;
    private String name;
}
