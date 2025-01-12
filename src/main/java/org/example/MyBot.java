package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

public class MyBot extends TelegramLongPollingBot {

    String state = "START";
    String firstName = "";
    String lastName = "";

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage sendMessage = new SendMessage();

        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            sendMessage.setChatId(chatId);

            try {
                switch (state) {
                    case "START":
                        sendMessage.setText("Please enter your first name");
                        state = "ENTER_FIRSTNAME";
                        break;

                    case "ENTER_FIRSTNAME":
                        firstName = text;
                        sendMessage.setText("Please enter your last name");
                        state = "ENTER_LASTNAME";
                        break;

                    case "ENTER_LASTNAME":
                        lastName = text;
                        sendMessage.setText("Please share your phone number");
                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setResizeKeyboard(true);

                        KeyboardButton keyboardButton = new KeyboardButton("Share your phone number");
                        keyboardButton.setRequestContact(true);

                        KeyboardRow keyboardRow = new KeyboardRow();
                        keyboardRow.add(keyboardButton);

                        replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        state = "SHARE_PHONE_NUMBER";
                        break;

                    case "SHARE_PHONE_NUMBER":
                        if (update.getMessage().hasContact() && update.getMessage().getContact() != null) {
                            String phoneNumber = update.getMessage().getContact().getPhoneNumber();
                            sendMessage.setText("Thank you! We received your phone number: " + phoneNumber);
                            state = "START";
                        } else {
                            sendMessage.setText("Please share your phone number using the button.");
                        }
                        break;

                    default:
                        sendMessage.setText("An error occurred. Please start again.");
                        state = "START";
                        break;
                }

                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ///////////////////////////

    @Override
    public String getBotUsername() {
        return "JavaExpenseTrackerBot";
    }

    @Override
    public String getBotToken() {
        return "7522572149:AAFspfXZZRqj4VpJf_fXx1YSoHAkPRUEE2Y";
    }
}
