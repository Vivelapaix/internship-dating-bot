package org.internship.dating.bot.service;

import org.internship.dating.bot.dao.ProjectDao;
import org.internship.dating.bot.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectDao projectDao;
    private final TransactionTemplate txTemplate;

    @Autowired
    public ProjectService(ProjectDao bookDao, TransactionTemplate txTemplate) {
        this.projectDao = bookDao;
        this.txTemplate = txTemplate;
    }

    public void saveProject(Project project) {
        txTemplate.executeWithoutResult(__ -> projectDao.insert(project));
    }

    public List<Project> getAllProjects() {
        return projectDao.fetchAll();
    }

    public List<Project> getAllCuratorProjects(long userId) {
        return projectDao.fetchByCurator(userId);
    }

}
