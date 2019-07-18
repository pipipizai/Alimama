package com.example.alimama.alimama.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.adapter.MessageAdapter;
import com.example.alimama.alimama.entity.Chat;
import com.example.alimama.alimama.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends BaseActvity {

    private DatabaseReference chatsReference;
    private DatabaseReference itemPublishedUserNamereference;

    private String username;
   // private String itemPublishedUserName;
    private String talkTo;

    private ImageButton btn_send;
    private EditText text_send;
    private ImageView profle_image;
    private TextView show_message;

    private MessageAdapter messageAapter;
    private List<Chat> mChat;

    private RecyclerView mMessageRecyclerView;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getChattingInformation();
        initView();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    sendMessage(username,talkTo,msg);
                    text_send.setText("");
                }else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_LONG).show();
                }
            }
        });

//        if(chatsReference.child()!=null) {
            itemPublishedUserNamereference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    readMessage(username, talkTo, user.getIcon());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//        }
    }

    private void readMessage(final String myUsername, final String itemPublishedUserName, final String imageurl){
        mChat = new ArrayList<>();


        chatsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if(chat.getReceiver().equals(myUsername)&&chat.getSender().equals(itemPublishedUserName) ||
                            chat.getReceiver().equals(itemPublishedUserName)&&chat.getSender().equals(myUsername)){
                        mChat.add(chat);
                    }
                }
                messageAapter = new MessageAdapter(MessageActivity.this,mChat,imageurl,username);
                mMessageRecyclerView.setAdapter(messageAapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void getChattingInformation() {

        Intent getIntent = getIntent();
       // itemPublishedUserName = getIntent.getStringExtra("itemPublishedUserName");
        talkTo = getIntent.getStringExtra("talkTo");
        username = getIntent.getStringExtra("username");
        itemPublishedUserNamereference = FirebaseDatabase.getInstance().getReference("Users").child(talkTo);
    }

    private void initView() {
        setUpToolbar();
        setTitle(talkTo);

        //itemPublishedUserNamereference = FirebaseDatabase.getInstance().getReference("Users").child(talkTo);
        chatsReference = FirebaseDatabase.getInstance().getReference("Chats");

        profle_image = findViewById(R.id.profile_image);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        mMessageRecyclerView = findViewById(R.id.message);
        mMessageRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(linearLayoutManager);

    }
}
