package com.example.news24;

public class RegisteredUser {
    private String username;
    private String password;
    private int type;
    private int notifications;

    public RegisteredUser(String username, String password, int type, int notifications) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.notifications = notifications;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }
}
