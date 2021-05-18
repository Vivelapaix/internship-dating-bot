package org.internship.dating.bot.bot.tointernship;

import org.internship.dating.bot.bot.SimpleLongPollingBot;
import org.internship.dating.bot.bot.common.BotCommand;
import org.internship.dating.bot.bot.common.TelegramUpdateMessage;
import org.internship.dating.bot.bot.tointernship.commands.ProjectAddCommand;
import org.internship.dating.bot.config.ToInternshipDatingBotSettings;
import org.internship.dating.bot.model.Project;
import org.internship.dating.bot.model.UserType;
import org.internship.dating.bot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.internship.dating.bot.model.Project.Builder.project;

@Component
public class ToInternshipDatingBot extends SimpleLongPollingBot<ToInternshipDatingBot, ToInternshipDatingBotSettings> {

    private final ProjectService projectService;

    @Autowired
    public ToInternshipDatingBot(ToInternshipDatingBotSettings settings, ProjectService projectService) {
        super(settings, ToInternshipDatingBot.class);
        this.projectService = projectService;
    }

    @Override
    public String getBotUsername() {
        return settings.getName();
    }

    @Override
    public String getBotToken() {
        return settings.getToken();
    }

    @Override
    protected void onMessageReceived(TelegramUpdateMessage message) {
        if (message.isChannelMessage()) {
            message.asChannelBotMessage(CommandName::byName).ifPresentOrElse(
                command -> executeCommand(
                    message.raw().getMessage().getFrom().getId(),
                    message.raw().getMessage().getChatId(),
                    command
                ),
                () -> log.warn("Failed to parse command: msg={}", message)
            );
        }
    }

    private void executeCommand(long userId, Long chatId, BotCommand<CommandName> command) {
        switch (command.name()) {
            case ALL:
                executeAllCommand(chatId);
                break;
            case ADD:
                executeAddCommand(userId, chatId, command);
                break;
            default:
                log.error("Unsupported command: command={}", command);
        }
    }

    private void executeAllCommand(Long chatId) {
        List<Project> projects = projectService.getAllProjects();
        String text = buildListProjectText(projects);
        SendMessage message = new SendMessage(chatId, text);
        sendMessage(message);
    }

    private void executeAddCommand(long userId, Long chatId, BotCommand<CommandName> command) {
        ProjectAddCommand.ProjectAddCommandData commandData = ProjectAddCommand.parseDataOrThrow(command.raw());
        Project projectToAdd = project()
            .title(commandData.title())
            .presentation(commandData.presentation())
            .description(commandData.description())
            .testTask(commandData.testTask())
            .userId(userId)
            .userType(UserType.CURATOR)
            .build();
        projectService.saveProject(projectToAdd);
        sendDone(chatId);
    }

    /*
    private void executeLinkAddCommand(Long chatId, BotCommand<CommandName> command) {
        LinkAddCommand.LinkAddCommandData data = ProjectAddCommand.parseData(command.raw());
        linkService.add(data.url(), false);
        sendDone(chatId);
    }
    */

    private String buildListProjectText(List<Project> projects) {
        if (projects.isEmpty()) {
            return "No projects.";
        }
        return projects.stream()
            .map(project ->
                String.format("%d. %s:\nDescription: %s\nPresentation: %s\nTest task: %s",
                    project.getId(),
                    project.getTitle(),
                    project.getDescription(),
                    project.getPresentation(),
                    project.getTestTask())
            )
            .collect(joining("\n\n"));
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send Telegram message: message=" + message, e);
        }
    }

    private void sendDone(Long chatId) {
        SendMessage message = new SendMessage(chatId, "done");
        sendMessage(message);
    }

}
