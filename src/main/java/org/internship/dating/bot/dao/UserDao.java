package org.internship.dating.bot.dao;

import org.internship.dating.bot.model.BotUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Map;

@Service
public class UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(@Nonnull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public void insert(@Nonnull BotUser botUser) {
        jdbcTemplate.update(
            "INSERT INTO bot_user (tg_uid, type, state) VALUES (:tg_uid, :type, :state)",
            Map.of(
                "tg_uid", botUser.getUid(),
                "type", botUser.getUserType().getValue(),
                "state", botUser.getUserState().getValue()
            )
        );
    }

}
