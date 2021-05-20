package org.internship.dating.bot.model;

public class ProjectRequest {

    private final long requestId;
    private final long projectId;
    private final String projectTitle;
    private final String projectPresentation;
    private final String projectDescription;
    private final String projectTestTask;
    private final String requestAuthorId;
    private final String requestAuthorName;
    private final ProjectRequestState projectRequestState;

    public ProjectRequest(long requestId,
                          long projectId,
                          String projectTitle,
                          String projectPresentation,
                          String projectDescription,
                          String projectTestTask,
                          String requestAuthorId,
                          ProjectRequestState projectRequestState,
                          String requestAuthorName) {
        this.requestId = requestId;
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectPresentation = projectPresentation;
        this.projectDescription = projectDescription;
        this.projectTestTask = projectTestTask;
        this.requestAuthorId = requestAuthorId;
        this.projectRequestState = projectRequestState;
        this.requestAuthorName = requestAuthorName;
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

    public ProjectRequestState getProjectRequestState() {
        return projectRequestState;
    }

    public String getRequestAuthorId() {
        return requestAuthorId;
    }

    public String getRequestAuthorName() {
        return requestAuthorName;
    }

    public static class Builder {
        private long requestId;
        private long projectId;
        private String projectTitle;
        private String projectPresentation;
        private String projectDescription;
        private String projectTestTask;
        private String requestAuthorId;
        private ProjectRequestState projectRequestState;
        private String requestAuthorName;

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

        public ProjectRequest.Builder requestAuthorId(String authorId) {
            this.requestAuthorId = authorId;
            return this;
        }

        public ProjectRequest.Builder projectRequestState(ProjectRequestState state) {
            this.projectRequestState = state;
            return this;
        }

        public ProjectRequest.Builder requestAuthorName(String requestAuthorName) {
            this.requestAuthorName = requestAuthorName;
            return this;
        }

        public ProjectRequest build() {
            return new ProjectRequest(
                requestId,
                projectId,
                projectTitle,
                projectPresentation,
                projectDescription,
                projectTestTask,
                requestAuthorId,
                projectRequestState,
                requestAuthorName
            );
        }
    }
}
