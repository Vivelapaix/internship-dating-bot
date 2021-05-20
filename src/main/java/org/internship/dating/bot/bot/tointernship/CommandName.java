package org.internship.dating.bot.bot.tointernship;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum CommandName {

    REGISTER("register"),

    VIEW_ALL_PROJECTS("view_all_projects"),

    VIEW_MY_PROJECT_REQUESTS("view_my_project_requests"),

    CREATE_PROJECT("create_project"),

    DELETE_PROJECT("delete_project"),

    EDIT_PROJECT("edit_project"),

    SEND_PROJECT_REQUEST("send_project_request"),

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

