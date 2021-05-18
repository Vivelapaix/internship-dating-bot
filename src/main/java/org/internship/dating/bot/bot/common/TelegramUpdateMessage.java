package org.internship.dating.bot.bot.common;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static org.internship.dating.bot.bot.common.TelegramMessageEntityType.BOT_COMMAND;

public class TelegramUpdateMessage {

    private final Update update;

    public TelegramUpdateMessage(Update update) {
        this.update = update;
    }

    public Update raw() {
        return update;
    }

    public boolean isChannelCommand() {
        return update.getChannelPost() != null && BOT_COMMAND.value.equals(update.getChannelPost().getEntities().get(0).getType());
    }

    public boolean isChannelMessage() {
        return update != null && update.hasMessage() && update.getMessage().hasText();
    }

    public boolean isCallbackQuery() {
        return update.getCallbackQuery() != null;
    }

    public <CommandNameT> Optional<BotCommand<CommandNameT>> asChannelBotCommand(
        Function<String, Optional<CommandNameT>> commandNameParser
    ) {
        String rawCommand = update.getChannelPost().getText().trim();
        List<String> commandTokens = Arrays.stream(rawCommand.split(" "))
            .map(String::trim)
            .filter(token -> !token.isBlank())
            .collect(Collectors.toList());
        if (commandTokens.size() == 0) {
            return empty();
        }
        return commandNameParser.apply(commandTokens.get(0).substring(1))
            .map(commandName -> BotCommand.Builder.<CommandNameT>command()
                .name(commandName)
                .args(commandTokens.subList(1, commandTokens.size()))
                .raw(rawCommand)
                .build());
    }

    public <CommandNameT> Optional<BotCommand<CommandNameT>> asChannelBotMessage(
        Function<String, Optional<CommandNameT>> commandNameParser
    ) {
        String rawCommand =  update.getMessage().getText().trim();
        List<String> commandTokens = Arrays.stream(rawCommand.split(" "))
            .map(String::trim)
            .filter(token -> !token.isBlank())
            .collect(Collectors.toList());
        if (commandTokens.size() == 0) {
            return empty();
        }
        return commandNameParser.apply(commandTokens.get(0).substring(1))
            .map(commandName -> BotCommand.Builder.<CommandNameT>command()
                .name(commandName)
                .args(commandTokens.subList(1, commandTokens.size()))
                .raw(rawCommand)
                .build());
    }

    @Override
    public String toString() {
        return "TelegramUpdateMessage{" +
            "update=" + update +
            '}';
    }

}

