package org.internship.dating.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    ToInternshipDatingBotSettings toInternshipDatingBotSettings(AppSettings settings) {
        return settings.getToInternshipDatingBotSettings();
    }

}
