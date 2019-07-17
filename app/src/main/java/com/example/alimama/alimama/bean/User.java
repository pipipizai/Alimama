package com.example.alimama.alimama.bean;

/**test
 * Created by LING on 6/6/2019.
 */

public class User {

   // private long id;
    private String password;
    private String username;
    private String icon;
    private String phone;
    private String address;

    public User(String password, String username, String icon, String phone, String address) {
        this.password = password;
        this.username = username;
        this.icon = icon;
        this.phone = phone;
        this.address = address;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
