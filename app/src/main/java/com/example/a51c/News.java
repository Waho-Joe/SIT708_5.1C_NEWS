package com.example.a51c;

public class News {

    private int id;
    private String title, imageUri,description, category;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public News(int id, String title, String imageUri, String description, String category) {
        this.id = id;
        this.title = title;
        this.imageUri = imageUri;
        this.description = description;
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUri() {
        return imageUri;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
