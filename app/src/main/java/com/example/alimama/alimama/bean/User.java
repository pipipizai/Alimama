package com.example.alimama.alimama.bean;

/**test
 * Created by LING on 6/6/2019.
 */

public class User {

    private long id;
    private String password;
    private String username;
    private String icon;


    public User(long id, String password, String username, String icon) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
