package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alimama.alimama.R;

public class MeInfoActivity extends BaseActvity{

    private TextView mUserID;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_info);

        setUpToolbar();
        setTitle("Personal Information");

        initView();
        initEvent();


    }

    private void initEvent() {

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        String userID= String.valueOf(preferences.getLong("userid",0));
        String username=preferences.getString("username", "No user");
        String password=preferences.getString("password", "No password");

        mUserID.setText(userID);
        mUsername.setText(username);
        mPassword.setText(password);



    }

    private void initView() {
        mUserID = findViewById(R.id.me_info_id);
        mUsername = findViewById(R.id.me_info_username);
        mPassword = findViewById(R.id.me_infor_password);
        mAddress = findViewById(R.id.me_info_adrress);
    }
}
