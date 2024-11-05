package com.aleksx.teleshorter.telegram;

import com.aleksx.teleshorter.shorter.ShorterLink;
import com.aleksx.teleshorter.text.UriFinder;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {

    private final String botUserName;
    private final UriFinder uriFinder;
    private final ShorterLink shorterLink;

    public TelegramBot(String botToken, String botUserName, UriFinder uriFinder, ShorterLink shorterLink) {
        super(botToken);
        this.botUserName = botUserName;
        this.uriFinder = uriFinder;
        this.shorterLink = shorterLink;
    }


    @Override
    public void onUpdateReceived(Update update) {
        this.updateProcessor(update);

    }


    @Override
    public String getBotUsername() {
        return botUserName;
    }


    private void updateProcessor(Update update) {
        var text = update.getMessage().getText();
        uriFinder.findUriInText(text)
                .concatMap(shorterLink::shortUri)
                .map(shortUrl -> String.format("[](%s) -> [](%s)", shortUrl.rUrl(), shortUrl.sUrl()))
                .reduce("", this::concatUrls)
                .doOnError(error -> {
                    System.err.println("Error on stream" + error);
                })
                .subscribe(msgUrl -> {
                    var newMessage = new SendMessage();
                    newMessage.setChatId(update.getMessage().getChatId().toString());
                    newMessage.setText(msgUrl);
                    newMessage.setParseMode(ParseMode.MARKDOWNV2);
                    try {
                        execute(newMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private String concatUrls(String str1, String str2) {
        return String.format("%s\n%s", str1, str2);
    }


}
