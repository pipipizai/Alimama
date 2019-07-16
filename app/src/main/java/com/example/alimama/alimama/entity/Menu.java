package com.example.alimama.alimama.entity;

public class Menu {

    public int ioon;
    public String menuName;

    public Menu() {
    }

    public Menu(int ioon, String menuName) {
        this.ioon = ioon;
        this.menuName = menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

}
