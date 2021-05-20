package org.internship.dating.bot;

import org.internship.dating.bot.config.AppSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.telegram.telegrambots.ApiContextInitializer;

@Import({
	AppSettings.class,
})
@SpringBootApplication
public class BotApplication {

	public static void main(String[] args) {
		//SpringApplication.run(BotApplication.class, args);

		ApiContextInitializer.init();
		SpringApplication.run(BotApplication.class, args);
	}

}
