package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MeInfoActivity extends BaseActvity{

    private TextView mUserID;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mAddress;
    private EditText mContact;

    private long userID2;
    private String UserIDString;
    private String username;
    private String password;
    private String address;
    private String contact;
    private Button mMeInfoAdd;

    private DatabaseReference mDatabaseUserInfo;

    private long meInfoAmount=0;
    private String userID;
    //private String username;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_me_info);

        initView();
        initEvent();

        setUpToolbar();
        setTitle("Personal Information");

        getMeInformation();
        displayMeInfomation();


        /**
         * edit information in my profile
         */

        mMeInfoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        username = mUsername.getText().toString();
                        password = mPassword.getText().toString();
                        address = mAddress.getText().toString();
                        contact = mContact.getText().toString();

                        //这是把信息放进数据库U
                        //mDatabaseUserInfo = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        mDatabaseUserInfo.child("username").setValue(username);
                        mDatabaseUserInfo.child(String.valueOf("password")).setValue(password);
                        mDatabaseUserInfo.child(String.valueOf("address")).setValue(address);
                        mDatabaseUserInfo.child(String.valueOf("contact")).setValue(contact);

            }
        });

    }


    private void displayMeInfomation() {

        mUsername.setText(username);
        mPassword.setText(password);
        mAddress.setText(address);
        mContact.setText(contact);
    }


    private void getMeInformation() {

        //get the transffered data
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){ //防止直接启动MainActivity时空指针闪退
            userID2 = bundle.getLong("id");
            UserIDString = String.valueOf(userID2);
        }

        username = mUsername.getText().toString();
        password = mPassword.getText().toString();
        address = mAddress.getText().toString();
        contact = mContact.getText().toString();

//        username=bundle.getString("name");
//        password=bundle.getString("name");
//        address=bundle.getString("name");
//        contact=bundle.getString("name");
//
//        ItemImage=bundle.getString("image");
//        itemName=bundle.getString("name");
//        itemPrice=bundle.getString("price");
//        itemDescription=bundle.getString("description");
//        itemPublishedUserID=bundle.getLong("itemPublishedUserID");

        //1、获取Preferences
        // 相当于本地缓存: userinfo里面有用户名/密码/用户id （地址和contact直接从数据库读取就好）
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        //2、取出数据 用户id和用户名
        userID = String.valueOf(preferences.getLong("userid",0));
        username=preferences.getString("username", null);


//        mDatabaseUserInfo.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    meInfoAmount = (dataSnapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }


    private void initEvent() {


        String address="Edit Address";
        String contact = "Edit Contact";
        //这里的addr & contact是从数据库读取的，不是用sharePreference的方法。
        //读取出来后存在value里
        //然后在存之前要有一个判断数据库里的数据是否为空的值（数据库里的addr contact 一开始是空的）

        mUserID.setText(userID);
        mUsername.setText(username);
        mPassword.setText(password);
        mUserID.setText(userID);

        mAddress.setText(address);
        mContact.setText(contact);


    }


    private void initView() {

        mUserID = findViewById(R.id.me_info_id);
        mUsername = findViewById(R.id.me_info_username);
        mPassword = findViewById(R.id.me_infor_password);
        mAddress = findViewById(R.id.me_info_adrress);
        mContact = findViewById(R.id. me_info_contact);
        mMeInfoAdd = findViewById(R.id.btn_save);

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        userID= String.valueOf(preferences.getLong("userid",0));
        username=preferences.getString("username", "No user");
        password=preferences.getString("password", "No password");

        //get database reference
        mDatabaseUserInfo = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
    }
}
