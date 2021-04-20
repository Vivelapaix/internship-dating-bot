package org.internship.dating.bot.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.url}")
    private String url;

    @Value("${db.driver}")
    private String driver;

    @Bean
    DataSource masterDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setConnectionTestQuery("SELECT 1");
        return new HikariDataSource(config);
    }

    @Bean
    @FlywayDataSource
    DataSource flywayDataSource(DataSource masterDataSource) {
        return masterDataSource;
    }

    @Bean
    JdbcTemplate masterJdbcTemplate(DataSource masterDataSource) {
        return new JdbcTemplate(masterDataSource);
    }

    @Bean(name = "txTemplate")
    TransactionTemplate masterTransactionTemplate(PlatformTransactionManager dbTransactionManager) {
        return new TransactionTemplate(dbTransactionManager);
    }

    @Bean
    PlatformTransactionManager dbTransactionManager(DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }
}
