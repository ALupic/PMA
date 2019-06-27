package com.example.news24;

public class Comment {
    private int id;
    private String content;
    private String time;
    private int likes;
    private int dislikes;
    private String user_username;
    private int article_id;

    public Comment(int id, String content, String time, int likes, int dislikes, String user_id, int article_id) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.likes = likes;
        this.dislikes = dislikes;
        this.user_username = user_id;
        this.article_id = article_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
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
