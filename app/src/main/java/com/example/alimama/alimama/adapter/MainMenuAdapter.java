package com.example.alimama.alimama.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.entity.Menu;

import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuViewHolder> {

    protected Context context;
    protected List<Menu> menus;

    public MainMenuAdapter(Context context, List<Menu> menus) {

        this.context = context;
        this.menus = menus;
    }

    @NonNull
    @Override
    public MainMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MainMenuViewHolder(LayoutInflater.from(context).inflate(R.layout.icon_main_menu, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuViewHolder mainMenuViewHolder, int i) {

        Menu menue = menus.get(i);
        mainMenuViewHolder.mImgMenuIcon.setImageResource(menue.ioon);
    }

    @Override
    public int getItemCount() {
        return null != menus ? menus.size() : 0;
    }
}

class MainMenuViewHolder extends RecyclerView.ViewHolder {

    public ImageView mImgMenuIcon;
    public TextView mTextMenuName;


    public MainMenuViewHolder(@NonNull View itemView) {
        super(itemView);
        mImgMenuIcon = itemView.findViewById(R.id.img_icon);
    }
}
