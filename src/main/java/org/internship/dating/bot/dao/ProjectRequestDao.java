package org.internship.dating.bot.dao;

import org.internship.dating.bot.model.ProjectRequestState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Map;

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

}
