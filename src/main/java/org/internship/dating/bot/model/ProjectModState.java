package org.internship.dating.bot.model;

import java.util.Arrays;

public enum ProjectModState {
    NEW(0, "проект создан"),
    TEST(1, "выдано тестовое задание"),
    REVIEW(2, "проверка тестовых заданий"),
    INTERVIEW(3, "собеседования"),
    WORK(4, "в работе"),
    RESULT(5, "проект завершен")
    ;

    private final int value;
    private final String description;

    ProjectModState(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public ProjectModState valueOf(int value) {
        return Arrays.stream(values())
            .filter(x -> x.getValue() == value)
            .findFirst().orElseThrow();
    }
}
