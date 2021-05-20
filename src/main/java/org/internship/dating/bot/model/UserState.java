package org.internship.dating.bot.model;

import java.util.Arrays;

public enum UserState {
    NEW(0),
    DELETED(1)
    ;

    private final int value;

    UserState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public UserState valueOf(int value) {
        return Arrays.stream(values())
            .filter(x -> x.getValue() == value)
            .findFirst().orElseThrow();
    }
}
