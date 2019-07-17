package com.example.alimama.alimama.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class PaymentActivity extends BaseActvity {

//    public static Object ExtraData;

    private TextView mItemID;
    private ImageView mItemImage;
    private TextView mItemName;
    private TextView mItemPrice;
    private TextView mItemDescription;
    private TextView mTextViewUsername;
    private TextView mTextViewPhone;
    private TextView mTextViewAddress;
    private Button mPay;
    private Button mItemInfoAddCart;


    private long itemID;
    private long itemPublishedUserID;
    private String ItemIDString;
    private String ItemImage;
    private String itemName;
    private String itemPrice;
    private String itemDescription;
    private String itemPublishedUserName;
    private String userProfileImage;
    private Notification notification;


    private DatabaseReference mDatabaseUserShoppingHistory;
    private DatabaseReference mDatabaseNotification;
    private DatabaseReference mDatabaseItems;
    private DatabaseReference mDatabaseUsers;

    private long shoppingHistoryItemsAmount=0;
    private long cartItemsAmount=0;
    private long userID=0;
    private long notificationAmount=0;
    private String username;
    private String message;
    private String phone;
    private String address;
    private String userIcon;
    private Item item;
    private Payment payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_confirmation);

        initView();

        TextView textView1 = findViewById(R.id.payment_name);
        TextView textView2 = findViewById(R.id.payment_phone);
        TextView textView3 = findViewById(R.id.payment_address);
        TextView textView4 = findViewById(R.id.choice1);
        TextView textView5 = findViewById(R.id.choice2);
        TextView textView6 = findViewById(R.id.choice3);
        TextView textView7 = findViewById(R.id.choice4);
        TextView textView8 = findViewById(R.id.payment_item_name);
        TextView textView9 = findViewById(R.id.payment_item_description);
        TextView textView10 = findViewById(R.id.payment_item_price);
        TextView textView11 = findViewById(R.id.payment);
        Typeface tf1= Typeface.createFromAsset(getAssets(), "PTSans-Regular.ttf");
        textView1.setTypeface(tf1);
        textView2.setTypeface(tf1);
        textView3.setTypeface(tf1);
        textView4.setTypeface(tf1);
        textView5.setTypeface(tf1);
        textView6.setTypeface(tf1);
        textView7.setTypeface(tf1);
        textView8.setTypeface(tf1);
        textView9.setTypeface(tf1);
        textView10.setTypeface(tf1);
        textView11.setTypeface(tf1);

        getInformation();

        displayInfomation();

        setUpToolbar();
        setTitle("Payment");

        createObject();

        /**
         * add item to my shopping history
         */
        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这两个语句你可以分别屏蔽运行程序看看是什么效果

                //这是把itemid（不是对象）放进数据库
//               mDatabaseUserFavorite.child(String.valueOf(itemID)).setValue(itemID);

                //这是把整个item对象放进数据库
                mDatabaseUserShoppingHistory.child(String.valueOf(itemID)).setValue(payment);

                mDatabaseUsers.child(itemPublishedUserName).child("sold items").child(String.valueOf(itemID)).setValue(payment);

                mDatabaseUsers.child(username).child("cart items").child(String.valueOf(itemID)).removeValue();
                mDatabaseUsers.child(username).child("favorite items").child(String.valueOf(itemID)).removeValue();

                mDatabaseUsers.child(itemPublishedUserName).child("published items").child(String.valueOf(itemID)).removeValue();

             //   mDatabaseUsers.child(username).child(String.valueOf(itemID)).removeValue();

                sendNotification();
                Toast.makeText(PaymentActivity.this, "Successfully pay", Toast.LENGTH_LONG).show();
                toPaymentInformationActivity();
                finish();
            }
        });

    }

    private void toPaymentInformationActivity(){

//        Intent intent = new Intent(PaymentActivity.this,PaymentInformationActivity.class);
////        intent.putExtra("reference","shopping history");
////        intent.putExtra("itemID", itemID);
//        String reference = "shopping history";
//        //the data need to transfer to search activity
//        Bundle bundle = new Bundle();
//        bundle.putString("reference",reference);
//        bundle.putLong("itemID",itemID);
//
//        intent.putExtras(bundle);
//        startActivity(intent);

        Intent intent = new Intent(PaymentActivity.this, PaymentInformationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("address",address);
        bundle.putString("buyer",username);
        bundle.putString("buyerProfileImage",userIcon);
        bundle.putString("description",item.getDescription());
        bundle.putString("image",item.getImage());
        bundle.putLong("item id",itemID);
        bundle.putString("phone",phone);
        bundle.putString("name",item.getName());
        bundle.putString("price",item.getPrice());
        bundle.putString("seller",item.getUserName());
        bundle.putString("sellerProfileImage",item.getUserProfileImage());
        bundle.putString("itemPublishedUserName",item.getUserName());
        intent.putExtras(bundle);

        startActivity(intent);


    }

    private void createObject(){
        //create a item object
        item = new Item();
        //set value in this item object(more detail look into Class Item)
        item.setName(itemName);
        item.setPrice(itemPrice);
        item.setDescription(itemDescription);
        item.setImage(ItemImage);
//        item.setUserID(itemPublishedUserID);
        item.setUserName(itemPublishedUserName);
        item.setItemID(itemID);
        item.setUserProfileImage(userProfileImage);

        payment = new Payment(itemName,itemPrice,itemDescription,
                ItemImage,username,itemPublishedUserName,
                itemID,userIcon,userProfileImage,phone,address);
    }

    private void displayInfomation() {

        Glide.with(this).load(ItemImage).placeholder(R.drawable.default_item_image).into(mItemImage);
        mItemName.setText(itemName);
        mItemPrice.setText(itemPrice);
        mItemDescription.setText(itemDescription);
        mTextViewUsername.setText(username);
        //mTextViewAddress.setText();
        mTextViewUsername.setText(username);
        mTextViewPhone.setText(phone);
        mTextViewAddress.setText(address);




    }


    private void getInformation() {

        Intent getIntent = getIntent();

        itemID = getIntent.getLongExtra("itemID",0);
        ItemImage = getIntent.getStringExtra("ItemImage");
        itemName = getIntent.getStringExtra("itemName");
        itemPrice = getIntent.getStringExtra("itemPrice");
        itemDescription = getIntent.getStringExtra("itemDescription");
        itemPublishedUserName = getIntent.getStringExtra("itemPublishedUserName");
        userProfileImage = getIntent.getStringExtra("userProfileImage");

        //1、获取Preferences
        // 相当于本地缓存: userinfo里面有用户名/密码/用户id （地址和contact直接从数据库读取就好）
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        //2、取出数据 用户id和用户名
        userID = preferences.getLong("userid",0);
        username=preferences.getString("username", null);
        phone=preferences.getString("phone", null);
        address=preferences.getString("address", null);
        userIcon =preferences.getString("userIcon", null);

      //  userPhone =

        //get database reference
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseNotification = FirebaseDatabase.getInstance().getReference().child("Notifications");
        mDatabaseUserShoppingHistory = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("shopping history");
//        mDatabaseUserCart = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("cart items");

        /**
         * Get total number of Buy items
         */
        mDatabaseUserShoppingHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    shoppingHistoryItemsAmount = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        /**
//         * Get total number of Buy items
//         */
//
//        mDatabaseNotification.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    notificationAmount = (dataSnapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    private void sendNotification(){
        HashMap<String, Object> hashMap = new HashMap<>();

        message= "Your item '"+itemName+"' is bought by "+ username +"."+'\n'
                + " Please go and talk to" + username;

        hashMap.put("buyer",username);
        hashMap.put("publishedItemUsername",itemPublishedUserName);
        hashMap.put("itemName",itemName);
        hashMap.put("message",message);

        mDatabaseNotification.push().setValue(hashMap);
        //notification = new Notification(username,itemName,itemPublishedUserName);
      //  mDatabaseNotification.child(String.valueOf(notificationAmount+1)).setValue(notification);
    }

    private void initView() {

        setUpToolbar();
        mTextViewUsername = findViewById(R.id.payment_name);
        mTextViewPhone = findViewById(R.id.payment_phone);
        mTextViewAddress = findViewById(R.id.payment_address);
        mItemImage = findViewById(R.id.payment_item_image);
        mItemName = findViewById(R.id.payment_item_name);
        mItemPrice = findViewById(R.id.payment_item_price);
        mItemDescription = findViewById(R.id.payment_item_description);
        mPay = findViewById(R.id.payment);
    }
}
