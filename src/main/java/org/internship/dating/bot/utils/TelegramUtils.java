package org.internship.dating.bot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TelegramUtils {

    public static InlineKeyboardButton inlineKeyboardButton(String label, String data) {
        InlineKeyboardButton button = new InlineKeyboardButton(label);
        button.setCallbackData(data);
        return button;
    }

    private TelegramUtils() {

    }
}

