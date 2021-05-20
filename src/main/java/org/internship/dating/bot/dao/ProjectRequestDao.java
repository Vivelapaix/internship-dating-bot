package org.internship.dating.bot.dao;

import org.internship.dating.bot.model.ProjectRequest;
import org.internship.dating.bot.model.ProjectRequestState;
import org.internship.dating.bot.model.UserType;
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

    public void insert(String userId, long projectId) {
        jdbcTemplate.update(
            "INSERT INTO project_request (tg_uid, project_id, state) VALUES (:tg_uid, :project_id, :state)",
            Map.of(
                "tg_uid", userId,
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

    public void changeRequestState(String userId, long projectId, ProjectRequestState state) {
        jdbcTemplate.update(
            "UPDATE project_request SET state = :state WHERE tg_uid = :tg_uid and project_id = :project_id",
            Map.of(
                "tg_uid", userId,
                "project_id", projectId,
                "state", state.getValue()
            )
        );
    }

    public List<ProjectRequest> fetchByUserId(String userId) {
        return jdbcTemplate.query(
            "SELECT p.id, p.title, p.presentation, p.description, p.test_task, pr.id request_id " +
                "FROM project_request pr " +
                "LEFT JOIN project p " +
                "ON pr.project_id = p.id " +
                "WHERE pr.state = 0 and p.state = 0 and pr.tg_uid = :user_id",
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

    public List<ProjectRequest> fetchByProjectAuthorId(String userId) {
        return jdbcTemplate.query(
            "SELECT bu.tg_login, pr.tg_uid, pr.project_id " +
                "FROM project p " +
                "INNER JOIN project_request pr " +
                "ON p.id = pr.project_id " +
                "INNER JOIN bot_user bu " +
                "ON pr.tg_uid = bu.tg_uid " +
                "WHERE p.state = 0 and p.user_type = :user_type and p.user_id = :user_id and pr.state = 0",
            Map.of(
                "user_type", UserType.CURATOR.getValue(),
                "user_id", Long.parseLong(userId)
            ),
            (rs, __) -> projectRequest()
                .requestAuthorId(rs.getString("tg_uid"))
                .projectId(rs.getLong("project_id"))
                .requestAuthorName(rs.getString("tg_login"))
                .build()
        );
    }

}
