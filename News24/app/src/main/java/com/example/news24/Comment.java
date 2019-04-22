package com.example.news24;

import java.util.Date;

public class Comment {
    private int id;
    private String content;
    private Date time;
    private int likes;
    private int dislikes;
    private int user_id;
    private int article_id;

    public Comment(int id, String content, String time, int likes, int dislikes, int user_id, int article_id) {
        this.id = id;
        this.content = content;
        this.time = new Date(time);
        this.likes = likes;
        this.dislikes = dislikes;
        this.user_id = user_id;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }
}
