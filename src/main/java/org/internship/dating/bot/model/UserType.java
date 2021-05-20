package org.internship.dating.bot.model;

import java.util.Arrays;

public enum UserType {
    STUDENT(0, "студент"),
    CURATOR(1, "руководитель практики"),
    ORGANIZER(2, "организатор практики")
    ;

    private final int value;
    private final String description;

    UserType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public UserType valueOf(int value) {
        return Arrays.stream(values())
            .filter(x -> x.getValue() == value)
            .findFirst().orElseThrow();
    }
}
