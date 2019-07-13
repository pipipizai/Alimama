package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Typeface;

import com.example.alimama.alimama.R;

public class MeInfoActivity extends BaseActvity{

    private TextView mUserID;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mAddress;
    private EditText mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_info);

        setUpToolbar();
        setTitle("My Information");


        initView();
        initEvent();

        TextView textView1 = findViewById(R.id.me_info_id);
        TextView textView2 = findViewById(R.id.me_info_username);
        TextView textView3 = findViewById(R.id.me_infor_password);
        TextView textView4 = findViewById(R.id.me_info_adrress);
        TextView textView5 = findViewById(R.id.user_id);
        TextView textView6 = findViewById(R.id.btn_save);
        Typeface tf1= Typeface.createFromAsset(getAssets(), "dry_brush.ttf");
        Typeface tf2= Typeface.createFromAsset(getAssets(), "againts.otf");
        textView1.setTypeface(tf1);
        textView2.setTypeface(tf1);
        textView3.setTypeface(tf1);
        textView4.setTypeface(tf1);
        textView5.setTypeface(tf1);
        textView6.setTypeface(tf2);


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
