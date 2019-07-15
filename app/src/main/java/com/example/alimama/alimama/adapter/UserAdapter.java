package com.example.alimama.alimama.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.User;
import com.example.alimama.alimama.ui.activity.MessageActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext;
    private List<User> mUser;
    private String imageurl;
    private String myUserName;
    private String userImageUrl;
    private DatabaseReference mDatabaseUser;

    public UserAdapter(Context mContext, List<User> mUser,String myUserName){
        this.mUser = mUser;
        this.mContext = mContext;
        this.myUserName=myUserName;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_list,viewGroup,false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int i) {

        final User user = mUser.get(i);

        holder.username.setText(user.getUsername());

        if(user.getIcon().equals("default")){
            holder.profile_images.setImageResource(R.mipmap.ic_launcher);
        }else {

           // mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
          //  Glide.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
            Glide.with(mContext).load(user.getIcon()).placeholder(R.drawable.default_item_image).into(holder.profile_images);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("itemPublishedUserName",user.getUsername());
                intent.putExtra("username",myUserName);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_images;

        public UserViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.message_username);
            profile_images = itemView.findViewById(R.id.message_profile_image);

        }
    }

}
