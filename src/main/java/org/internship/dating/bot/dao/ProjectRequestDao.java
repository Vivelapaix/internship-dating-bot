package org.internship.dating.bot.dao;

import org.internship.dating.bot.model.ProjectRequest;
import org.internship.dating.bot.model.ProjectRequestState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import static org.internship.dating.bot.model.ProjectRequest.Builder.projectRequest;

@Service
public class ProjectRequestDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProjectRequestDao(@Nonnull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public void insert(String userName, long projectId) {
        jdbcTemplate.update(
            "INSERT INTO project_request (user_name, project_id, state) VALUES (:user_name, :project_id, :state)",
            Map.of(
                "user_name", userName,
                "project_id", projectId,
                "state", ProjectRequestState.NEW.getValue()
            )
        );
    }

    public void changeRequestState(long requestId, ProjectRequestState state) {
        jdbcTemplate.update(
            "UPDATE project_request SET state = :state WHERE id = :id",
            Map.of("id", requestId, "state", state.getValue())
        );
    }

    public List<ProjectRequest> fetchByUserId(String userId) {
        return jdbcTemplate.query(
            "SELECT p.id, p.title, p.presentation, p.description, p.test_task, pr.id request_id " +
                "FROM project_request pr " +
                "LEFT JOIN project p " +
                "ON pr.project_id = p.id " +
                "WHERE pr.state = 0 and p.state = 0 and pr.user_name = :user_id",
            Map.of(
                "user_id", userId
            ),
            (rs, __) -> projectRequest()
                .requestId(rs.getLong("request_id"))
                .projectId(rs.getLong("id"))
                .projectTitle(rs.getString("title"))
                .projectPresentation(rs.getString("presentation"))
                .projectDescription(rs.getString("description"))
                .projectTestTask(rs.getString("test_task"))
                .build()
        );
    }

}
