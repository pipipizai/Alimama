package com.example.alimama.alimama.bean;

public class Item {


    private String name;
    private String price;
    private String description;
    private String image;
    private long userID;
    private long itemID;

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }



    public Item() {
    }

    public Item(String name, String price, String description, String image, long userID,long itemID) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.userID = userID;
        this.itemID=itemID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
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
