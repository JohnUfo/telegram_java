package org.example;

public class TelegramState {
    private Long chatId;
    private String state = "START";
    private String firstName;
    private String lastName;

    public TelegramState(Long chatId, String state, String firstName, String lastName) {
        this.chatId = chatId;
        this.state = state;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public TelegramState() {}

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
