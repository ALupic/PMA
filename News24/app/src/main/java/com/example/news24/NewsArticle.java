package com.example.news24;

public class NewsArticle {
    private int id;
    private String category;
    private String title;
    private String image;
    private String content;
    private int likes;
    private int dislikes;
    private String lat;
    private String longg;

    public NewsArticle(int id, String category, String title, String image, String content, int likes, int dislikes, String lat, String longg) {
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongg() {
        return longg;
    }

    public void setLongg(String longg) {
        this.longg = longg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
