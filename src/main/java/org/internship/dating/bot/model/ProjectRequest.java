package org.internship.dating.bot.model;

public class ProjectRequest {

    private final long requestId;
    private final long projectId;
    private final String projectTitle;
    private final String projectPresentation;
    private final String projectDescription;
    private final String projectTestTask;

    public ProjectRequest(long requestId,
                          long projectId,
                          String projectTitle,
                          String projectPresentation,
                          String projectDescription,
                          String projectTestTask) {
        this.requestId = requestId;
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectPresentation = projectPresentation;
        this.projectDescription = projectDescription;
        this.projectTestTask = projectTestTask;
    }

    public long getRequestId() {
        return requestId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectPresentation() {
        return projectPresentation;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getProjectTestTask() {
        return projectTestTask;
    }

    public long getProjectId() {
        return projectId;
    }

    public static class Builder {
        private long requestId;
        private long projectId;
        private String projectTitle;
        private String projectPresentation;
        private String projectDescription;
        private String projectTestTask;

        public Builder() {
        }

        public static ProjectRequest.Builder projectRequest() {
            return new ProjectRequest.Builder();
        }

        public ProjectRequest.Builder requestId(long id) {
            this.requestId = id;
            return this;
        }

        public ProjectRequest.Builder projectId(long projectId) {
            this.projectId = projectId;
            return this;
        }

        public ProjectRequest.Builder projectTitle(String title) {
            this.projectTitle = title;
            return this;
        }

        public ProjectRequest.Builder projectPresentation(String presentation) {
            this.projectPresentation = presentation;
            return this;
        }

        public ProjectRequest.Builder projectDescription(String description) {
            this.projectDescription = description;
            return this;
        }

        public ProjectRequest.Builder projectTestTask(String testTask) {
            this.projectTestTask = testTask;
            return this;
        }

        public ProjectRequest build() {
            return new ProjectRequest(
                requestId,
                projectId,
                projectTitle,
                projectPresentation,
                projectDescription,
                projectTestTask
            );
        }
    }
}
