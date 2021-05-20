package org.internship.dating.bot.model;

import java.util.Arrays;

public enum ProjectRequestState {
    NEW(0, "новая заявка"),
    DELETED(1, "заявка удалена"),
    APPROVED(2, "автор заявки одобрен на проект"),
    REJECTED(3, "заявка отклонена")
    ;

    private final int value;
    private final String description;

    ProjectRequestState(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public ProjectRequestState valueOf(int value) {
        return Arrays.stream(values())
            .filter(x -> x.getValue() == value)
            .findFirst().orElseThrow();
    }
}
