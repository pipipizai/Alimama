package com.example.alimama.alimama.util;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.example.alimama.alimama.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    /**
     * @param context
     * @param icons
     * @return
     */
    //用来添加数据
    public static List<ImageView> getHeaderAdInfo(Context context, int[] icons) {
        List<ImageView> datas = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            ImageView icon = new ImageView(context);
            icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            icon.setImageResource(icons[i]);
            datas.add(icon);
        }


        return datas;
    }

    /**
     * main menue
     * @param icons
     * @return
     */
    public static List<Menu> getMainMenu(int icons[],String[] names) {
        List<Menu> menus = new ArrayList<>();

        for (int i = 0; i < icons.length; i++) {
            Menu menu = new Menu(icons[i],names[i]);
            menus.add(menu);
        }

        return menus;
    }


}
