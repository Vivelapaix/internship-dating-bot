package org.internship.dating.bot.service;

import org.internship.dating.bot.dao.ProjectRequestDao;
import org.internship.dating.bot.model.Project;
import org.internship.dating.bot.model.ProjectRequest;
import org.internship.dating.bot.model.ProjectRequestState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class ProjectRequestService {
    private final ProjectRequestDao requestDao;
    private final TransactionTemplate txTemplate;

    @Autowired
    public ProjectRequestService(ProjectRequestDao requestDao, TransactionTemplate txTemplate) {
        this.requestDao = requestDao;
        this.txTemplate = txTemplate;
    }

    public void saveRequest(String userName, long projectId) {
        txTemplate.executeWithoutResult(__ -> requestDao.insert(userName, projectId));
    }

    public void deleteRequest(long requestId) {
        txTemplate.executeWithoutResult(__ -> requestDao.changeRequestState(requestId, ProjectRequestState.DELETED));
    }

    public List<ProjectRequest> getAllUserRequests(String userId) {
        return requestDao.fetchByUserId(userId);
    }
}
