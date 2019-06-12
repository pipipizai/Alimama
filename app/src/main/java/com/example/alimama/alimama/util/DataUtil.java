package com.example.alimama.alimama.util;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

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

}
