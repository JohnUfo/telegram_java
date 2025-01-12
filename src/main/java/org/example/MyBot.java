package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    List<TelegramState> users = new ArrayList<>();

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage sendMessage = new SendMessage();

        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            TelegramState currentUser = findUser(chatId);


            sendMessage.setChatId(chatId);

            if (update.getMessage().getText() != null && update.getMessage().getText().equals("/start")) {
                currentUser.setState(UserState.START);
            }

            try {
                switch (currentUser.getState()) {
                    case UserState.START:
                        sendMessage.setText("Please enter your first name");
                        currentUser.setState(UserState.FIRST_NAME);
                        break;

                    case UserState.FIRST_NAME:
                        currentUser.setFirstName(text);
                        sendMessage.setText("Please enter your last name");
                        currentUser.setState(UserState.LAST_NAME);
                        break;

                    case UserState.LAST_NAME:
                        currentUser.setLastName(text);
                        sendMessage.setText("Please share your phone number");
                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setResizeKeyboard(true);

                        KeyboardButton keyboardButton = new KeyboardButton("Share your phone number");
                        keyboardButton.setRequestContact(true);

                        KeyboardRow keyboardRow = new KeyboardRow();
                        keyboardRow.add(keyboardButton);

                        replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        currentUser.setState(UserState.SHARE_PHONE_NUMBER);
                        break;

                    case UserState.SHARE_PHONE_NUMBER:
                        if (update.getMessage().hasContact() && update.getMessage().getContact() != null) {
                            String phoneNumber = update.getMessage().getContact().getPhoneNumber();
                            sendMessage.setText("Thank you! We received your phone number: " + phoneNumber);

                            ReplyKeyboardRemove removeKeyboard = new ReplyKeyboardRemove();
                            removeKeyboard.setRemoveKeyboard(true);
                            sendMessage.setReplyMarkup(removeKeyboard);

                            currentUser.setState(UserState.START);
                        } else {
                            sendMessage.setText("Please share your phone number using the button.");
                        }
                        break;

                    default:
                        sendMessage.setText("An error occurred. Please start again.");
                        currentUser.setState(UserState.START);
                        break;
                }

                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //////////////////////////////////////////

    private TelegramState findUser(Long chatId) {
        TelegramState currentUser = null;
        for (TelegramState user : users) {
            if (user.getChatId().equals(chatId)) {
                currentUser = user;
            }
        }

        if (currentUser == null) {
            currentUser = new TelegramState();
            currentUser.setChatId(chatId);
            users.add(currentUser);
        }
        return currentUser;
    }

    @Override
    public String getBotUsername() {
        return "JavaExpenseTrackerBot";
    }

    @Override
    public String getBotToken() {
        return "7522572149:AAFspfXZZRqj4VpJf_fXx1YSoHAkPRUEE2Y";
    }
}
