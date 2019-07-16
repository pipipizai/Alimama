package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.adapter.NotificationAdapter;
import com.example.alimama.alimama.bean.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends BaseActvity {

    private DatabaseReference notificationReference;
    private DatabaseReference itemPublishedUserNameReference;

    private String username;
    private String itemPublishedUserName;

    private ImageView profle_image;
    private TextView show_message;

    private NotificationAdapter notificationAdapter;
    private List<Notification> mNotificationList;

    private RecyclerView mNotificationRecyclerView;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

       // getChattingInformation();
        initView();

        readMessage();

    }

    private void readMessage(){
        mNotificationList = new ArrayList<>();

        notificationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mNotificationList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Notification notification = snapshot.getValue(Notification.class);

                    if(notification.getBuyer().equals(username)){
                        mNotificationList.add(notification);
                    }
                }
                notificationAdapter = new NotificationAdapter(NotificationActivity.this,mNotificationList);
                mNotificationRecyclerView.setAdapter(notificationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


//    private void getChattingInformation() {
//
//        Intent getIntent = getIntent();
//        // itemPublishedUserName = getIntent.getStringExtra("itemPublishedUserName");
//        username = getIntent.getStringExtra("username");
//
//    }

    private void initView() {
        setUpToolbar();
        setTitle("Notification");

        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        username=preferences.getString("username", null);

        notificationReference = FirebaseDatabase.getInstance().getReference().child("Notifications");

        mNotificationRecyclerView = findViewById(R.id.notificationMessage);
        mNotificationRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        mNotificationRecyclerView.setLayoutManager(linearLayoutManager);




    }
}
