package org.internship.dating.bot.bot.tointernship.commands;

import org.internship.dating.bot.bot.tointernship.CommandName;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendRequestCommand {
    private static Pattern COMMAND_PATTERN = Pattern.compile(
        "^/" + CommandName.SEND_PROJECT_REQUEST.name + " (?<projectId>\\d+)$"
    );

    @Nonnull
    public static Long parseData(@Nonnull String commandLine) {
        Matcher matcher = COMMAND_PATTERN.matcher(commandLine);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse command: command=" + commandLine);
        }
        try {
            return Long.valueOf(matcher.group("projectId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse command: command=" + commandLine, e);
        }
    }
}
