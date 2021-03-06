package org.internship.dating.bot.bot.tointernship;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public enum ProjectCallbackDataType {

    PROJECT_INFO("project_info"),

    GET_CURATOR_PROJECT_INFO("get_curator_project_info"),

    EDIT_CURATOR_PROJECT_INFO("edit_curator_project_info"),

    DELETE_CURATOR_PROJECT("delete_curator_project_info"),

    CHOOSE_PROJECT("choose_project"),

    REQUEST_PROJECT("request_project"),

    PROJECT_REQUEST_INFO("project_request_info"),

    VIEW_MY_PROJECT_REQUESTS("view_my_project_requests"),

    CANCEL_PROJECT_REQUEST("cancel_project_request"),

    GET_USER_PROJECT_REQUEST_INFO("get_user_project_request_info"),

    APPROVE_USER_ON_PROJECT("approve_user_on_project"),

    ;

    private static final Map<String, ProjectCallbackDataType> commandByName;

    static {
        commandByName = Arrays.stream(ProjectCallbackDataType.values())
            .collect(Collectors.toMap(command -> command.name, identity()));
    }

    public final String name;

    ProjectCallbackDataType(String name) {
        this.name = name;
    }

    public static Optional<ProjectCallbackDataType> byName(String name) {
        return Optional.ofNullable(commandByName.get(name));
    }
}
