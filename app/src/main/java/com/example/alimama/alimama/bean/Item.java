package com.example.alimama.alimama.bean;

public class Item {


    private String name;
    private String price;
    private String description;
    private String image;

    public Item() {
    }

    public Item(String image, String name, String price, String description) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
