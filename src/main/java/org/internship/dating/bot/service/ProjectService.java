package org.internship.dating.bot.service;

import org.internship.dating.bot.model.Project;
import org.internship.dating.bot.model.ProjectModState;
import org.internship.dating.bot.model.ProjectState;
import org.internship.dating.bot.model.UserType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.internship.dating.bot.model.Project.Builder.project;

@Service
public class ProjectService {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProjectService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public void saveProject(Project project) {
        jdbcTemplate.update(
            "INSERT INTO project (title, presentation, description, test_task, user_type, user_id, state, mod_state) " +
                "VALUES (:title, :presentation, :description, :test_task, :user_type, :user_id, :state, :mod_state)",
            Map.of(
                "title", project.getTitle(),
                "presentation", project.getPresentation(),
                "description", project.getDescription(),
                "test_task", project.getTestTask(),
                "user_type", project.getUserType().getValue(),
                "user_id", project.getUserId(),
                "state", ProjectState.NEW.getValue(),
                "mod_state", ProjectModState.NEW.getValue()
            )
        );
    }

    public List<Project> getAllCuratorProjects(long userId) {
        return jdbcTemplate.query(
            "SELECT title, presentation, description, test_task " +
                "FROM project " +
                "WHERE state = :state and user_type = :user_type and user_id = :user_id",
            Map.of(
                "state", ProjectState.NEW.getValue(),
                "user_type", UserType.CURATOR.getValue(),
                "user_id", userId
            ),
            (rs, rowNum) -> project()
                .title(rs.getString("title"))
                .presentation(rs.getString("presentation"))
                .description(rs.getString("description"))
                .testTask(rs.getString("test_task"))
                .build()
        );
    }

}
