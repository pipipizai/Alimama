package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Typeface;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;




public class MeInfoActivity extends BaseActvity{

    private EditText mUsername;
    private EditText mPassword;
    private EditText mAddress;
    private EditText mContact;
    private ImageView mUserIcon;
    private Uri mImageUri = null;

    private String username;
    private String password;
    private String address;
    private String contact;
    private Button mMeInfoAdd;
    private String userIcon;

    private DatabaseReference mDatabaseUserInfo;

    View view;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_me_info);

        setUpToolbar();
        setTitle("My Information");
        initEvent();


        initView();
        getMeInformation();



       // displayMeInfomation();

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
                mDatabaseUserInfo.child("username").setValue(username);
                mDatabaseUserInfo.child("password").setValue(password);
                mDatabaseUserInfo.child("address").setValue(address);
                mDatabaseUserInfo.child("contact").setValue(contact);

                Toast.makeText(MeInfoActivity.this, "Save successfully", Toast.LENGTH_LONG).show();

                finish();
                Intent intent = new Intent(MeInfoActivity.this,MeInfoActivity.class);
                startActivity(intent);

            }
        });
    }

    private void displayMeInfomation() {

        mUsername.setText(username);
        mPassword.setText(password);
        mAddress.setText(address);
        mContact.setText(contact);

        Glide.with(this).load(userIcon).placeholder(R.drawable.default_item_image).into(mUserIcon);
    }

    private void getMeInformation() {

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        username=preferences.getString("username", null);

        mDatabaseUserInfo = FirebaseDatabase.getInstance().getReference().child("Users").child(username);

        mDatabaseUserInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                password=user.getPassword();
                contact=user.getPhone();
                address=user.getAddress();
                userIcon=user.getIcon();

                mUsername.setText(username);
                mPassword.setText(password);
                mAddress.setText(address);
                mContact.setText(contact);

                Glide.with(MeInfoActivity.this).load(userIcon).placeholder(R.drawable.default_item_image).into(mUserIcon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initEvent() {

        //   TextView textView1 = findViewById(R.id.me_info_id);
        TextView textView2 = findViewById(R.id.me_info_username);
        TextView textView3 = findViewById(R.id.me_infor_password);
        TextView textView4 = findViewById(R.id.me_info_adrress);
        TextView textView5 = findViewById(R.id.me_info_contact);
        TextView textView6 = findViewById(R.id.btn_save);
        Typeface tf1= Typeface.createFromAsset(getAssets(), "PTSans-Regular.ttf");
        //   textView1.setTypeface(tf1);
        textView2.setTypeface(tf1);
        textView3.setTypeface(tf1);
        textView4.setTypeface(tf1);
        textView5.setTypeface(tf1);
        textView6.setTypeface(tf1);
    }

    private void initView() {

        mUsername = findViewById(R.id.me_info_username);
        mPassword = findViewById(R.id.me_infor_password);
        mAddress = findViewById(R.id.me_info_adrress);
        mContact = findViewById(R.id. me_info_contact);
        mMeInfoAdd = findViewById(R.id.btn_save);
        mUserIcon = findViewById(R.id.user_icon);

    }
}
