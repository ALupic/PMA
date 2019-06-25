package com.example.news24;

public class Category {
    private int id;
    private String title;
    private int selected;

    public Category(int id, String title, int selected) {
        this.id = id;
        this.title = title;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
