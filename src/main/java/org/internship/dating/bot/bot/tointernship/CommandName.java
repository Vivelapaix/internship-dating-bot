package org.internship.dating.bot.bot.tointernship;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum CommandName {

    START("start"),

    VIEW_ALL_PROJECTS("view_all_projects"),

    CREATE_PROJECT("create_project"),

    DELETE_PROJECT("delete_project"),

    EDIT_PROJECT("edit_project"),

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

