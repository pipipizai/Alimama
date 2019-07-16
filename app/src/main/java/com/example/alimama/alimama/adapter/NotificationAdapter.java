package com.example.alimama.alimama.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.Chat;
import com.example.alimama.alimama.bean.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Notification> mNotification;
    private String userName;

    public NotificationAdapter(Context mContext, List<Notification> mNotification){
        this.mNotification = mNotification;
        this.mContext = mContext;
      //  this.userName = userName;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

            View view = LayoutInflater.from(mContext).inflate(R.layout.notification_left,parent,false);
            return new NotificationAdapter.ViewHolder(view);
    }


    @NonNull
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position){

        Notification notification= mNotification.get(position);

        holder.show_message.setText(notification.getMessage());



    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
       // public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
           // profile_image = itemView.findViewById(R.id.profile_image);

        }
    }



    @Override
    public int getItemCount() {
        return mNotification.size();
    }

}
