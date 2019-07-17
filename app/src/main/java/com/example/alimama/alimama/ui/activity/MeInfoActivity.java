package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;




public class MeInfoActivity extends BaseActvity{

    private TextView mUserID;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mAddress;
    private EditText mContact;
    private ImageView mUserIcon;
    private Uri mImageUri = null;


    private long userID2;
    private String UserIDString;

    private String username;
    private String password;
    private String address;
    private String contact;
    private Button mMeInfoAdd;
    private String userIcon;

    private DatabaseReference mDatabaseUserInfo;

    private long meInfoAmount=0;
    private String userID;
    //private String username;
    View view;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_me_info);

        setUpToolbar();
        setTitle("My Information");

        initView();
        initEvent();

        getMeInformation();
        displayMeInfomation();

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

                Toast.makeText(MeInfoActivity.this, "Save successfully", Toast.LENGTH_LONG).show();

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
        address = String.valueOf(mDatabaseUserInfo.child("address").getKey());
        contact = String.valueOf(mDatabaseUserInfo.child("contact").getKey());
      //  contact = mContact.getText().toString();



//        username=bundle.getString("username");
//        password=bundle.getString("password");
//        address=bundle.getString("address");
//        contact=bundle.getString("contact");


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

//        //1、获取Preferences
//        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
//        //2、取出数据
//        String userID= String.valueOf(preferences.getLong("userid",0));
//        String username=preferences.getString("username", "No user");
//        String password=preferences.getString("password", "No password");
//
//        mUserID.setText(userID);
//        mUsername.setText(username);
//        mPassword.setText(password);

        //这里的addr & contact是从数据库读取的，不是用sharePreference的方法。
        //读取出来后存在value里
        //然后在存之前要有一个判断数据库里的数据是否为空的值（数据库里的addr contact 一开始是空的）


    //    mUserID.setText(userID);
        mUsername.setText(username);
        mPassword.setText(password);

        mAddress.setText(address);
        mContact.setText(contact);

        Glide.with(this).load(userIcon).placeholder(R.drawable.default_item_image).into(mUserIcon);



    }

    private void initView() {

      //  mUserID = findViewById(R.id.me_info_id);
        mUsername = findViewById(R.id.me_info_username);
        mPassword = findViewById(R.id.me_infor_password);
        mAddress = findViewById(R.id.me_info_adrress);
        mContact = findViewById(R.id. me_info_contact);
        mMeInfoAdd = findViewById(R.id.btn_save);
        mUserIcon = findViewById(R.id.user_icon);

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        userID= String.valueOf(preferences.getLong("userid",0));

        username=preferences.getString("username", "No user");
        password=preferences.getString("password", "No password");

        userIcon=preferences.getString("userIcon", null);

        address=preferences.getString("address", "No address");
        contact=preferences.getString("contact", "No contact");


        //get database reference
        mDatabaseUserInfo = FirebaseDatabase.getInstance().getReference().child("Users").child(username);

    }
}
