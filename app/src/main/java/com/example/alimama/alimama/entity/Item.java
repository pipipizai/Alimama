package com.example.alimama.alimama.entity;

public class Item {


    private String name;
    private String price;
    private String description;
    private String image;
    private String userName;
    private long itemID;
    private String userProfileImage;


    public Item() {
    }

    public Item(String name, String price, String description, String image, String userName, long itemID, String userProfileImage) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.userName = userName;
        this.itemID = itemID;
        this.userProfileImage = userProfileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName= userName;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
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

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }
}
