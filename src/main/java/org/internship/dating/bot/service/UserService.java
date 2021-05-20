package org.internship.dating.bot.service;

import org.internship.dating.bot.dao.UserDao;
import org.internship.dating.bot.model.BotUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class UserService {
    private final UserDao userDao;
    private final TransactionTemplate txTemplate;

    @Autowired
    public UserService(UserDao userDao, TransactionTemplate txTemplate) {
        this.userDao = userDao;
        this.txTemplate = txTemplate;
    }

    public void saveUser(BotUser user) {
        txTemplate.executeWithoutResult(__ -> userDao.insert(user));
    }

}
