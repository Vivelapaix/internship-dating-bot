package org.internship.dating.bot.bot.tointernship;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.internship.dating.bot.bot.SimpleLongPollingBot;
import org.internship.dating.bot.bot.common.BotCommand;
import org.internship.dating.bot.bot.common.TelegramUpdateMessage;
import org.internship.dating.bot.bot.tointernship.commands.ProjectAddCommand;
import org.internship.dating.bot.bot.tointernship.commands.ProjectDeleteCommand;
import org.internship.dating.bot.bot.tointernship.commands.SendRequestCommand;
import org.internship.dating.bot.config.ToInternshipDatingBotSettings;
import org.internship.dating.bot.model.BotUser;
import org.internship.dating.bot.model.Project;
import org.internship.dating.bot.model.ProjectRequest;
import org.internship.dating.bot.model.UserState;
import org.internship.dating.bot.model.UserType;
import org.internship.dating.bot.service.ProjectRequestService;
import org.internship.dating.bot.service.ProjectService;
import org.internship.dating.bot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.internship.dating.bot.bot.tointernship.ProjectCallbackDataType.CANCEL_PROJECT_REQUEST;
import static org.internship.dating.bot.bot.tointernship.ProjectCallbackDataType.CHOOSE_PROJECT;
import static org.internship.dating.bot.bot.tointernship.ProjectCallbackDataType.PROJECT_INFO;
import static org.internship.dating.bot.bot.tointernship.ProjectCallbackDataType.PROJECT_REQUEST_INFO;
import static org.internship.dating.bot.bot.tointernship.ProjectCallbackDataType.REQUEST_PROJECT;
import static org.internship.dating.bot.bot.tointernship.ProjectCallbackDataType.VIEW_MY_PROJECT_REQUESTS;
import static org.internship.dating.bot.model.BotUser.Builder.user;
import static org.internship.dating.bot.model.Project.Builder.project;
import static org.internship.dating.bot.utils.TelegramUtils.inlineKeyboardButton;

@Component
public class ToInternshipDatingBot extends SimpleLongPollingBot<ToInternshipDatingBot, ToInternshipDatingBotSettings> {

    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectRequestService requestService;

    @Autowired
    public ToInternshipDatingBot(ToInternshipDatingBotSettings settings,
                                 ProjectService projectService,
                                 UserService userService,
                                 ProjectRequestService requestService) {
        super(settings, ToInternshipDatingBot.class);
        this.projectService = projectService;
        this.userService = userService;
        this.requestService = requestService;
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
                    message.raw().getMessage().getFrom().getId().toString(),
                    message.raw().getMessage().getChatId(),
                    command
                ),
                () -> log.warn("Failed to parse command: msg={}", message)
            );
        } else if (message.isCallbackQuery()) {
            Long chatId = message.raw().getCallbackQuery().getMessage().getChatId();
            Integer userId = message.raw().getCallbackQuery().getFrom().getId();
            List<String> data = Arrays.stream(message.raw().getCallbackQuery().getData().split(" ")).collect(toList());
            ProjectCallbackDataType.byName(data.get(0))
                .ifPresentOrElse(
                    callback -> executeProjectCallback(chatId, userId, callback, data.subList(1, data.size()), message.raw().getCallbackQuery().getMessage()),
                    () -> executeUnknownCallback(chatId)
                );
        }
    }

    private void executeProjectCallback(Long chatId, Integer userId, ProjectCallbackDataType callbackDataType, List<String> params, Message original) {
        switch (callbackDataType) {
            case PROJECT_INFO:
                sendProjectInfo(chatId, Long.parseLong(params.get(0)), userId.toString());
                break;
            case CHOOSE_PROJECT:
                sendProjectsKeyboard(chatId);
                break;
            case REQUEST_PROJECT:
                sendProjectRequest(chatId, Long.parseLong(params.get(0)), params.get(1));
                break;
            case PROJECT_REQUEST_INFO:
                sendProjectRequestInfo(chatId, Long.parseLong(params.get(0)), params.get(1), params.get(2));
                break;
            case VIEW_MY_PROJECT_REQUESTS:
                sendUserProjectRequestsKeyboard(chatId, userId.toString());
                break;
            case CANCEL_PROJECT_REQUEST:
                cancelProjectRequest(chatId, Long.parseLong(params.get(0)));
                break;
            default:
                executeUnsupportedCommand(chatId);
        }
    }

    private void executeCommand(String userId, Long chatId, BotCommand<CommandName> command) {
        switch (command.name()) {
            case VIEW_ALL_PROJECTS:
                sendProjectsKeyboard(chatId);
                break;
            case VIEW_MY_PROJECT_REQUESTS:
                sendUserProjectRequestsKeyboard(chatId, userId);
                break;
            case REGISTER:
                saveNewBotUser(userId);
                break;
            case CREATE_PROJECT:
                executeAddCommand(userId, chatId, command);
                break;
            case DELETE_PROJECT:
                executeDeleteProject(userId, chatId, command);
                break;
            case SEND_PROJECT_REQUEST:
                executeSendProjectRequest(userId, chatId, command);
                break;
            default:
                log.error("Unsupported command: command={}", command);
        }
    }

    private void sendProjectInfo(Long chatId, long projectId, String userId) {
        Project project = projectService.getProjectById(projectId);
        SendMessage message = new SendMessage(chatId, buildProjectText(project));
        ImmutableList<InlineKeyboardButton> actionButtons = ImmutableList.of(
            actionButton("Подать заявку", REQUEST_PROJECT.name + " " + projectId + " " + userId),
            actionButton("К проектам", CHOOSE_PROJECT.name)
        );
        message.setReplyMarkup(projectRequestKeyboard(projectId, userId, actionButtons));
        sendMessage(message);
    }

    private void sendProjectsKeyboard(Long chatId) {
        SendMessage message = new SendMessage(chatId, "Список проектов");
        message.setReplyMarkup(chooseProjectsKeyboard());
        sendMessage(message);
    }

    private void sendUserProjectRequestsKeyboard(Long chatId, String userId) {
        SendMessage message = new SendMessage(chatId, "Мои заявки на проекты:");
        message.setReplyMarkup(chooseUserProjectRequestsKeyboard(userId));
        sendMessage(message);
    }

    private void sendProjectRequest(Long chatId, long projectId, String userId) {
        requestService.saveRequest(userId, projectId);
        sendDone(chatId);
    }

    private void cancelProjectRequest(Long chatId, long requestId) {
        requestService.deleteRequest(requestId);
        sendDone(chatId);
    }

    private void sendProjectRequestInfo(Long chatId, long projectId, String userId, String requestId) {
        Project project = projectService.getProjectById(projectId);
        SendMessage message = new SendMessage(chatId, buildProjectText(project));
        ImmutableList<InlineKeyboardButton> actionButtons = ImmutableList.of(
            actionButton("Отменить заявку", CANCEL_PROJECT_REQUEST.name + " " + requestId),
            actionButton("Назад", VIEW_MY_PROJECT_REQUESTS.name)
        );
        message.setReplyMarkup(projectRequestKeyboard(projectId, userId, actionButtons));
        sendMessage(message);
    }

    private InlineKeyboardMarkup chooseProjectsKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<Project> projects = projectService.getAllProjects();
        Iterable<List<InlineKeyboardButton>> buttons = Iterables.partition(
            () -> projects.stream()
                .map(project ->
                    inlineKeyboardButton(project.getTitle(), PROJECT_INFO.name + " " + project.getId()))
                .iterator(),
            1
        );
        markup.setKeyboard(StreamSupport.stream(buttons.spliterator(), false).collect(toList()));
        return markup;
    }

    private InlineKeyboardMarkup chooseUserProjectRequestsKeyboard(String userId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<ProjectRequest> requests = requestService.getAllUserRequests(userId);
        Iterable<List<InlineKeyboardButton>> buttons = Iterables.partition(
            () -> requests.stream()
                .map(request ->
                    inlineKeyboardButton(request.getProjectTitle(),
                        PROJECT_REQUEST_INFO.name + " " + request.getProjectId() + " " + userId + " " + request.getRequestId()))
                .iterator(),
            1
        );
        markup.setKeyboard(StreamSupport.stream(buttons.spliterator(), false).collect(toList()));
        return markup;
    }

    private InlineKeyboardMarkup projectRequestKeyboard(long projectId, String userId, ImmutableList<InlineKeyboardButton> actionButtons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(singletonList(actionButtons));
        return markup;
    }

    private InlineKeyboardButton actionButton(String buttonName, String callBackString) {
        InlineKeyboardButton button = new InlineKeyboardButton(buttonName);
        button.setCallbackData(callBackString);
        return button;
    }

    private void executeSendProjectRequest(String userId, Long chatId, BotCommand<CommandName> command) {
        Long projectId = SendRequestCommand.parseData(command.raw());
        requestService.saveRequest(userId, projectId);
        sendDone(chatId);
    }

    private void executeDeleteProject(String userId, Long chatId, BotCommand<CommandName> command) {
        Long projectId = ProjectDeleteCommand.parseData(command.raw());
        projectService.deleteProject(projectId);
        sendDone(chatId);
    }

    private void executeAddCommand(String userId, Long chatId, BotCommand<CommandName> command) {
        ProjectAddCommand.ProjectAddCommandData commandData = ProjectAddCommand.parseDataOrThrow(command.raw());
        Project projectToAdd = project()
            .title(commandData.title())
            .presentation(commandData.presentation())
            .description(commandData.description())
            .testTask(commandData.testTask())
            .userId(Long.parseLong(userId))
            .userType(UserType.CURATOR)
            .build();
        projectService.saveProject(projectToAdd);
        sendDone(chatId);
    }

    private void saveNewBotUser(String userId) {
        BotUser userToAdd = user()
            .name(userId)
            .userType(UserType.STUDENT)
            .userState(UserState.NEW)
            .build();
        userService.saveUser(userToAdd);
    }

    private String buildProjectText(Project project) {
        if (project == null) {
            return "Empty project";
        }
        return String.format("%d. %s:\nDescription: %s\nPresentation: %s\nTest task: %s",
            project.getId(), project.getTitle(), project.getDescription(),
            project.getPresentation(), project.getTestTask());
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

    private void executeUnknownCallback(Long chatId) {
        sendMessage(new SendMessage(chatId, "unknown callback"));
    }

    private void executeUnsupportedCommand(Long chatId) {
        sendMessage(new SendMessage(chatId, "unsupported command"));
    }

}
