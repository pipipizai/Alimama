package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.Item;
import com.example.alimama.alimama.bean.Notification;
import com.example.alimama.alimama.bean.Payment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PaymentInformationActivity extends BaseActvity {

//    public static Object ExtraData;

        private TextView mItemID;
        private ImageView mItemImage;
        private ImageView mUserProfileImage;
        private TextView mItemName;
        private TextView mItemPrice;
        private TextView mItemDescription;
        private TextView mTextViewUsername;
        private TextView mTextViewPhone;
        private TextView mTextViewAddress;
        private ImageButton mChat;

        private long itemID;
        private String talkTo;

        private DatabaseReference mDatabaseUsers;

        private String username;
        private String referenceName;

        private String address;
        private String buyer;
        private String buyerProfileImage;
        private String description;
        private String image;
        private String phone;
        private String name;
        private String price;
        private String seller;
        private String sellerProfileImage;


    private Payment payment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_payment_information);



//            TextView textView1 = findViewById(R.id.payment_info_user_name);
//            TextView textView2 = findViewById(R.id.payment_info_user_contact);
//            TextView textView3 = findViewById(R.id.payment_info_user_address);
//            TextView textView4 = findViewById(R.id.choice1);
//            TextView textView5 = findViewById(R.id.choice2);
//            TextView textView6 = findViewById(R.id.choice3);
//            TextView textView7 = findViewById(R.id.choice4);
//            TextView textView8 = findViewById(R.id.payment_info_item_name);
//            TextView textView9 = findViewById(R.id.payment_info_item_info_description);
//            TextView textView10 = findViewById(R.id.payment_info_item_info_price);
//            TextView textView11 = findViewById(R.id.payment_info_conncet_seller);
//            Typeface tf1= Typeface.createFromAsset(getAssets(), "PTSans-Regular.ttf");
//            textView1.setTypeface(tf1);
//            textView2.setTypeface(tf1);
//            textView3.setTypeface(tf1);
//            textView4.setTypeface(tf1);
//            textView5.setTypeface(tf1);
//            textView6.setTypeface(tf1);
//            textView7.setTypeface(tf1);
//            textView8.setTypeface(tf1);
//            textView9.setTypeface(tf1);
//            textView10.setTypeface(tf1);
//            textView11.setTypeface(tf1);

            setUpToolbar();
            setTitle("Payment Information");

            initView();

            getInformation();

            createObject();

            displayInfomation();

            /**
             * chat begin
             */
            mChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PaymentInformationActivity.this,MessageActivity.class);
                    intent.putExtra("talkTo",talkTo);
                    intent.putExtra("username", username);
                    startActivity(intent);

                }
            });

        }

        private void createObject(){

            payment = new Payment(name,price,description,
                    image,buyer,seller,
                    itemID,buyerProfileImage,sellerProfileImage,phone,address);

            if(username.equals(payment.getBuyer())) {
                talkTo = payment.getSeller();
            }else {
                talkTo = payment.getBuyer();
            }
        }

        private void displayInfomation(){

            mItemName.setText(payment.getName());
            mItemPrice.setText(payment.getPrice());
            mItemDescription.setText(payment.getDescription());
            mTextViewUsername.setText(payment.getBuyer());
            mTextViewPhone.setText(payment.getPhone());
            mTextViewAddress.setText(payment.getAddress());

            Glide.with(this).load(payment.getImage()).placeholder(R.drawable.default_item_image).into(mItemImage);
            Glide.with(this).load(payment.getBuyerProfileImage()).placeholder(R.drawable.default_item_image).into(mUserProfileImage);



        }

        private void getInformation(){

        //    Bundle bundle = this.getIntent().getExtras();
            Bundle bundle = this.getIntent().getExtras();
           // itemID = getIntent.getLongExtra("itemID",0);
//           // referenceName = getIntent.getStringExtra("reference","shopping history");
//            referenceName =bundle.getString("reference");
//            itemID = bundle.getLong("itemID");
            address = bundle.getString("address");
            buyer = bundle.getString("buyer");
            buyerProfileImage = bundle.getString("buyerProfileImage");
            description = bundle.getString("description");
            image = bundle.getString("image");
            itemID = bundle.getLong("item id");
            phone = bundle.getString("phone");
            name = bundle.getString("name");
            price = bundle.getString("price");
            seller = bundle.getString("seller");
            sellerProfileImage = bundle.getString("sellerProfileImage");

           // reference =  bundle.getString("reference");

//            payment = new Payment(name,price,description,
//                    image,buyer,seller,
//                    itemID,buyerProfileImage,sellerProfileImage,phone,address);

//            if(reference=="shopping history"){
//                buyer = username;
//            }else {
//                buyer = seller;
//            }


            SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);

            //2、取出数据 用户id和用户名
            username=preferences.getString("username", null);

            mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        }


        private void initView() {

            setUpToolbar();
            mTextViewUsername = findViewById(R.id.payment_info_user_name);
            mTextViewPhone = findViewById(R.id.payment_info_user_contact);
            mTextViewAddress = findViewById(R.id.payment_info_user_address);
            mUserProfileImage = findViewById(R.id.payment_info_user_image);

            mItemName = findViewById(R.id.payment_info_item_name);
            mItemPrice = findViewById(R.id.payment_info_item_info_price);
            mItemImage = findViewById(R.id.payment_info_item_info_image);
            mItemDescription = findViewById(R.id.payment_info_item_info_description);

            mChat = findViewById(R.id.payment_info_image_contact_user);
        }


}
