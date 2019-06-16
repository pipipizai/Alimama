package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alimama.alimama.R;

public class MeInfoActivity extends BaseActvity{

    private EditText mUsername;
    private EditText mPassword;
    private EditText mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_info);

        setUpToolbar();
        initView();
        initEvent();

        setTitle("Personal Information");
    }

    private void initEvent() {

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        String username=preferences.getString("username", "defaultname");
        String password=preferences.getString("password", "password");

        mUsername.setText(username);
        mPassword.setText(password);


    }

    private void initView() {
        mUsername = (EditText)findViewById(R.id.me_info_username);
        mPassword = (EditText)findViewById(R.id.me_infor_password);
        mAddress = (EditText)findViewById(R.id.me_info_adrress);
    }
}
