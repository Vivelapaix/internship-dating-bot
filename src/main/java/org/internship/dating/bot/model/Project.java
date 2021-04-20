package org.internship.dating.bot.model;

public class Project {
    private final long id;
    private final String title;
    private final String presentation;
    private final String description;
    private final String testTask;
    private final UserType userType;
    private final long userId;
    private final ProjectState state;
    private final ProjectModState modState;

    public Project(long id,
                   String title,
                   String presentation,
                   String description,
                   String testTask,
                   UserType userType,
                   long userId,
                   ProjectState state,
                   ProjectModState modState) {
        this.id = id;
        this.title = title;
        this.presentation = presentation;
        this.description = description;
        this.testTask = testTask;
        this.userType = userType;
        this.userId = userId;
        this.state = state;
        this.modState = modState;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPresentation() {
        return presentation;
    }

    public String getDescription() {
        return description;
    }

    public String getTestTask() {
        return testTask;
    }

    public UserType getUserType() {
        return userType;
    }

    public long getUserId() {
        return userId;
    }

    public ProjectState getState() {
        return state;
    }

    public ProjectModState getModState() {
        return modState;
    }

    public static class Builder {
        private long id;
        private String title;
        private String presentation;
        private String description;
        private String testTask;
        private UserType userType;
        private long userId;
        private ProjectState state;
        private ProjectModState modState;

        public Builder() {
        }

        public static Builder project() {
            return new Builder();
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder presentation(String presentation) {
            this.presentation = presentation;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder testTask(String testTask) {
            this.testTask = testTask;
            return this;
        }

        public Builder userType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder state(ProjectState state) {
            this.state = state;
            return this;
        }

        public Builder modState(ProjectModState modState) {
            this.modState = modState;
            return this;
        }

        public Project build() {
            return new Project(
                id,
                title,
                presentation,
                description,
                testTask,
                userType,
                userId,
                state,
                modState
            );
        }
    }

}
