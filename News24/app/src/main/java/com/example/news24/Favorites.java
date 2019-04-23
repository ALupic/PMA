package com.example.news24;

public class Favorites {
    private int id;

    private String user_username;
    private int article_id;

    public Favorites(int id, String user_id, int article_id) {
        this.id = id;
        this.user_username = user_id;
        this.article_id = article_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_username;
    }

    public void setUser_id(String user_id) {
        this.user_username = user_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }
}
