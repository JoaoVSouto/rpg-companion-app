package com.example.rpgcompanion.model;

public class Notification {

    private String theme;
    private String description;

    public Notification(String theme, String description) {
        this.theme = theme;
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return theme + " - " + description;
    }
}
