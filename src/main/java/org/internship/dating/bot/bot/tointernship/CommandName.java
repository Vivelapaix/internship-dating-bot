package org.internship.dating.bot.bot.tointernship;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum CommandName {

    ALL("all"),

    ADD("add"),

    ;

    private static final Map<String, CommandName> commandByName;

    static {
        commandByName = Arrays.stream(CommandName.values())
            .collect(toMap(command -> command.name, identity()));
    }

    public final String name;

    CommandName(String value) {
        this.name = value;
    }

    public static Optional<CommandName> byName(String name) {
        return Optional.ofNullable(commandByName.get(name));
    }
}

