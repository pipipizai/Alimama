package com.example.alimama.alimama.bean;

/**test
 * Created by LING on 6/6/2019.
 */

public class User {

    private int id;
    private String password;
    private String username;
    private int icon;

    public User(int id, String password, String username, int icon) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.icon = icon;
    }

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
