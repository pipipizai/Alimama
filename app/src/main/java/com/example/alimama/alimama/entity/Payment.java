package com.example.alimama.alimama.entity;

public class Payment {

    private String name;
    private String price;
    private String description;
    private String image;
    private String buyer;
    private String seller;
    private long itemID;
    private String buyerProfileImage;
    private String sellerProfileImage;
    private String phone;
    private String address;

    public Payment() {
    }

    public Payment(String name, String price, String description, String image, String buyer, String seller, long itemID, String buyerProfileImage, String sellerProfileImage, String phone, String address) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.buyer = buyer;
        this.seller = seller;
        this.itemID = itemID;
        this.buyerProfileImage = buyerProfileImage;
        this.sellerProfileImage = sellerProfileImage;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getBuyerProfileImage() {
        return buyerProfileImage;
    }

    public void setBuyerProfileImage(String buyerProfileImage) {
        this.buyerProfileImage = buyerProfileImage;
    }

    public String getSellerProfileImage() {
        return sellerProfileImage;
    }

    public void setSellerProfileImage(String sellerProfileImage) {
        this.sellerProfileImage = sellerProfileImage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
