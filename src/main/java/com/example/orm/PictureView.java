package com.example.orm;

public class PictureView {
    private final String id;
    private final String pictureUrl;
    private final String description;
    private final String userLogin;

    public PictureView(String id, String pictureUrl, String description, String userName) {
        this.id = id;
        this.pictureUrl = pictureUrl;
        this.description = description;
        this.userLogin = userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getId() {
        return id;
    }
}
