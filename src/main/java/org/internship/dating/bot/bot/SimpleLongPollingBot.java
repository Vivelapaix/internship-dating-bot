package org.internship.dating.bot.bot;

import org.internship.dating.bot.bot.common.TelegramUpdateMessage;
import org.internship.dating.bot.config.BotSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class SimpleLongPollingBot<Self extends SimpleLongPollingBot<Self, SettingsT>, SettingsT extends BotSettings>
    extends TelegramLongPollingBot {

    protected final Logger log;

    protected final SettingsT settings;

    protected SimpleLongPollingBot(SettingsT settings, Class<Self> clazz) {
        this.log = LoggerFactory.getLogger(clazz);
        this.settings = settings;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Update: {}", update);
        onMessageReceived(new TelegramUpdateMessage(update));
    }

    protected abstract void onMessageReceived(TelegramUpdateMessage message);

    @Override
    public String getBotUsername() {
        return settings.getName();
    }

    @Override
    public String getBotToken() {
        return settings.getToken();
    }

}
