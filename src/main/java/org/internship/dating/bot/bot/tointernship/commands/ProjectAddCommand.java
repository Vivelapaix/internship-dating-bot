package org.internship.dating.bot.bot.tointernship.commands;

import org.apache.commons.lang3.StringUtils;
import org.internship.dating.bot.bot.tointernship.CommandName;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;
import static org.internship.dating.bot.bot.tointernship.commands.ProjectAddCommand.ProjectAddCommandData.Builder.projectAddCommandData;

public class ProjectAddCommand {

    private static Pattern COMMAND_PATTERN = Pattern.compile(
        "^/" + CommandName.CREATE_PROJECT.name + " (?<title>(([\\S]+)|([\"'“”‘’].+[\"'“”‘’]))) " +
            "(?<presentation>(([\\S]+)|([\"'“”‘’].+[\"'“”‘’]))) " +
            "(?<description>(([\\S]+)|([\"'“”‘’].+[\"'“”‘’]))) " +
            "(?<testTask>(([\\S]+)|([\"'“”‘’].+[\"'“”‘’])))$"
    );

    @Nonnull
    public static ProjectAddCommandData parseDataOrThrow(@Nonnull String commandLine) {
        Matcher matcher = COMMAND_PATTERN.matcher(commandLine);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse command: command=" + commandLine);
        }
        try {
            return projectAddCommandData()
                .title(StringUtils.strip(matcher.group("title"), "\"'“”‘’"))
                .presentation(StringUtils.strip(matcher.group("presentation"), "\"'“”‘’"))
                .description(StringUtils.strip(matcher.group("description"), "\"'“”‘’"))
                .testTask(StringUtils.strip(matcher.group("testTask"), "\"'“”‘’"))
                .build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse command: command=" + commandLine, e);
        }
    }

    public static class ProjectAddCommandData {

        private final String title;
        private final String presentation;
        private final String description;
        private final String testTask;

        private ProjectAddCommandData(
            @Nonnull String title,
            @Nonnull String presentation,
            @Nonnull String description,
            @Nonnull String testTask
        ) {
            this.title = requireNonNull(title, "title");
            this.presentation = requireNonNull(presentation, "presentation");
            this.description = requireNonNull(description, "description");
            this.testTask = requireNonNull(testTask, "testTask");
        }

        @Nonnull
        public String title() {
            return title;
        }

        @Nonnull
        public String presentation() {
            return presentation;
        }

        @Nonnull
        public String description() {
            return description;
        }

        @Nonnull
        public String testTask() {
            return testTask;
        }

        @Nonnull
        @Override
        public String toString() {
            return "ProjectAddCommandData{" +
                "title='" + title + '\'' +
                ", presentation='" + presentation + '\'' +
                ", description='" + description + '\'' +
                ", testTask='" + testTask + '\'' +
                '}';
        }

        public static class Builder {

            private String title;
            private String presentation;
            private String description;
            private String testTask;

            private Builder() {
            }

            @Nonnull
            public static Builder projectAddCommandData() {
                return new Builder();
            }

            @Nonnull
            public Builder title(@Nonnull String title) {
                this.title = title;
                return this;
            }

            @Nonnull
            public Builder presentation(@Nonnull String presentation) {
                this.presentation = presentation;
                return this;
            }

            @Nonnull
            public Builder description(@Nonnull String description) {
                this.description = description;
                return this;
            }

            @Nonnull
            public Builder testTask(@Nonnull String testTask) {
                this.testTask = testTask;
                return this;
            }

            @Nonnull
            public ProjectAddCommandData build() {
                return new ProjectAddCommandData(
                    title,
                    presentation,
                    description,
                    testTask
                );
            }

        }

    }

}
