package com.example.instagram.models;

public class Posts {
    private String description;
    private String imageUrl;
    private String userId;

    public Posts() {
        //empty
    }

    public Posts(String description, String imageUrl, String userId) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
