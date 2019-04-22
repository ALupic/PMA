package com.example.news24;

import java.io.Serializable;

public class NewsArticle implements Serializable {
    private int id;
    private String category;
    private String title;
    private String image;
    private String content;
    private int likes;
    private int dislikes;
    private float lat;
    private float longg;

    public NewsArticle(int id, String category, String title, String image, String content, int likes, int dislikes, float lat, float longg) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.image = image;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.lat = lat;
        this.longg = longg;
    }

    public NewsArticle(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLongg() {
        return longg;
    }

    public void setLongg(float longg) {
        this.longg = longg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
