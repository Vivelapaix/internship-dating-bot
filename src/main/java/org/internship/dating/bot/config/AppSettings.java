package org.internship.dating.bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@ConfigurationProperties
public class AppSettings {

    private ToInternshipDatingBotSettings toInternshipDatingBotSettings;

    public ToInternshipDatingBotSettings getToInternshipDatingBotSettings() {
        return toInternshipDatingBotSettings;
    }

    public void setToInternshipDatingBotSettings(ToInternshipDatingBotSettings toInternshipDatingBotSettings) {
        this.toInternshipDatingBotSettings = toInternshipDatingBotSettings;
    }
}