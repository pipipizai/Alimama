package com.example.alimama.alimama.bean;

public class Notification {

    private String buyer;
    private String itemName;
    private String message;
    private String publishedItemUsername;

    public Notification() {
    }

    public Notification(String buyer, String itemName, String message, String publishedItemUsername) {
        this.buyer = buyer;
        this.itemName = itemName;
        this.message = message;
        this.publishedItemUsername = publishedItemUsername;
    }


    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        message = message;
    }

    public String getPublishedItemUsername() {
        return publishedItemUsername;
    }

    public void setPublishedItemUsername(String publishedItemUsername) {
        this.publishedItemUsername = publishedItemUsername;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        itemName = itemName;
    }
}
