package org.internship.dating.bot.controller;

import org.internship.dating.bot.model.Project;
import org.internship.dating.bot.model.UserType;
import org.internship.dating.bot.service.ProjectService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.internship.dating.bot.model.Project.Builder.project;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/UID/{userId}/all", method = RequestMethod.GET)
    public List<Project> getCuratorProjects(@PathVariable("userId") long userId) {
        projectService.saveProject(project()
            .title("Internship Dating Bot")
            .presentation("Presentation")
            .testTask("Test Task")
            .description("Internship Dating Bot")
            .userType(UserType.CURATOR)
            .userId(userId)
            .build());
        return projectService.getAllCuratorProjects(userId);
    }

}
