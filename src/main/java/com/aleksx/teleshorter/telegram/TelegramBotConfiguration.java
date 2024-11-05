package com.aleksx.teleshorter.telegram;


import com.aleksx.teleshorter.shorter.ShorterLink;
import com.aleksx.teleshorter.text.UriFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration(proxyBeanMethods = false)
public class TelegramBotConfiguration {

    @Bean
    public TelegramBot telegramBot(TelegramBotProps telegramBotProps,
                                   ShorterLink shorterLink,
                                   UriFinder uriFinder) throws TelegramApiException {
        var bot = new TelegramBot(telegramBotProps.getToken(), telegramBotProps.getName(), uriFinder, shorterLink);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        return bot;
    }


}
