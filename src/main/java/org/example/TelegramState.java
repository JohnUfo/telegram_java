package org.example;

public class TelegramState {
    private Long chatId;
    private String state = "START";
    private User user;

    public TelegramState(Long chatId, String state, User user) {
        this.chatId = chatId;
        this.state = state;
        this.user = user;
    }

    public TelegramState() {
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TelegramState{" +
                "chatId=" + chatId +
                ", state='" + state + '\'' +
                ", user=" + user +
                '}';
    }
}
