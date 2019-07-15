package com.example.alimama.alimama.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.adapter.UserAdapter;
import com.example.alimama.alimama.bean.Chat;
import com.example.alimama.alimama.bean.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private RecyclerView mUserMessageRecyclerView;

    private String username;
    private UserAdapter userAdapter;
    private List<User> mUsers;

    private DatabaseReference mDatabaseChatsReference;
    private DatabaseReference mDatabaseUsersReference;

    private List<String> usersList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message,container,false);

//        //字体
//        TextView textView1 = getView().findViewById(R.id.cart_head);
//        Typeface tf= Typeface.createFromAsset(getContext().getAssets(), "againts.otf");
//        textView1.setTypeface(tf);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        mDatabaseChatsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if(chat.getSender().equals(username)){
                        usersList.add(chat.getReceiver());
                    }
                    if (chat.getReceiver().equals(username)){
                        usersList.add(chat.getSender());
                    }
                }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void readChats(){
        mUsers = new ArrayList<>();

        mDatabaseUsersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    for(String userNameInList:usersList){
                        if (user.getUsername().equals(userNameInList)){
                            if(mUsers.size()!=0){
                                for(User userInmUsers:mUsers){
                                    if(!user.getUsername().equals(userInmUsers.getUsername())){
                                        mUsers.add(user);
                                    }
                                }
                            }else{
                                mUsers.add(user);
                            }
                        }
                    }

                }
                userAdapter = new UserAdapter(getContext(),mUsers,username);
                mUserMessageRecyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public void initView() {

        mUserMessageRecyclerView = getView().findViewById(R.id.recyclerview_user_message);
        mUserMessageRecyclerView.setHasFixedSize(true);
        mUserMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usersList = new ArrayList<>();

        mDatabaseChatsReference = FirebaseDatabase.getInstance().getReference("Chats");
        mDatabaseUsersReference = FirebaseDatabase.getInstance().getReference("Users");

        //1、获取Preferences
        SharedPreferences preferences=getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        username=preferences.getString("username", null);





    }
}
