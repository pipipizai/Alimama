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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity extends BaseActvity {

//    public static Object ExtraData;

    private TextView mItemID;
    private ImageView mItemImage;
    private TextView mItemName;
    private TextView mItemPrice;
    private TextView mItemDescription;
    private Button mItemInfoAddShoppingHistory;
    private Button mItemInfoAddCart;


    private long itemID;
    private long itemPublishedUserID;
    private String ItemIDString;
    private String ItemImage;
    private String itemName;
    private String itemPrice;
    private String itemDescription;
    private String itemPublishedUserName;


    private DatabaseReference mDatabaseUserShoppingHistory;
    private DatabaseReference mDatabaseUserCart;
    private DatabaseReference mDatabaseItems;

    private long shoppingHistoryItemsAmount=0;
    private long cartItemsAmount=0;
    private long userID=0;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_confirmation);

        initView();

        getItemInformation();

        displayItemInfomation();

        setUpToolbar();
        setTitle("Payment");

        //字体
        TextView textView1 = findViewById(R.id.payment);
//        TextView textView2 = findViewById(R.id.item_info_add_cart);
//        TextView textView3 = findViewById(R.id.conncet_seller);

        Typeface tf1= Typeface.createFromAsset(getAssets(), "againts.otf");

        textView1.setTypeface(tf1);

//        textView2.setTypeface(tf1);
//        textView3.setTypeface(tf1);

        //create a item object
        final Item item = new Item();
        //set value in this item object(more detail look into Class Item)
        item.setName(itemName);
        item.setPrice(itemPrice);
        item.setDescription(itemDescription);
        item.setImage(ItemImage);
//        item.setUserID(itemPublishedUserID);
        item.setUserName(itemPublishedUserName);
        item.setItemID(itemID);

        /**
         * add item to my shopping history
         //         */
        mItemInfoAddShoppingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这两个语句你可以分别屏蔽运行程序看看是什么效果

                //这是把itemid（不是对象）放进数据库
//               mDatabaseUserFavorite.child(String.valueOf(itemID)).setValue(itemID);

                //这是把整个item对象放进数据库
                mDatabaseUserShoppingHistory.child(String.valueOf(itemID)).setValue(item);
            }
        });
//
//        /**
//         * add item to my cart
//         */
//        mItemInfoAddCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //mDatabaseUserCart.child(String.valueOf("item "+(cartItemsNumber +1)+" id ")).setValue(itemID);
//                mDatabaseUserCart.child(String.valueOf(itemID)).setValue(item);
//            }
//        });
//

    }

    private void displayItemInfomation() {

        Glide.with(this).load(ItemImage).placeholder(R.drawable.default_item_image).into(mItemImage);
        mItemName.setText(itemName);
        mItemPrice.setText(itemPrice);
        mItemDescription.setText(itemDescription);
    }


    private void getItemInformation() {

        Intent getIntent = getIntent();

        itemID = getIntent.getLongExtra("itemID",0);
        ItemImage = getIntent.getStringExtra("ItemImage");
        itemName = getIntent.getStringExtra("itemName");
        itemPrice = getIntent.getStringExtra("itemPrice");
        itemDescription = getIntent.getStringExtra("itemDescription");
        itemPublishedUserName = getIntent.getStringExtra("itemPublishedUserName");



//        //get the transffered data
//        Bundle bundle = this.getIntent().getExtras();
//        if (bundle != null){ //防止直接启动MainActivity时空指针闪退
//
//            itemID = bundle.getLong("item id");
////            ItemIDString = String.valueOf(itemID);
//        }
//
//        ItemImage=bundle.getString("image");
//        itemName=bundle.getString("name");
//        itemPrice=bundle.getString("price");
//        itemDescription=bundle.getString("description");
//
////        itemPublishedUserID=bundle.getLong("itemPublishedUserID");
//
//        itemPublishedUserName=bundle.getString("itemPublishedUserName");


        //1、获取Preferences
        // 相当于本地缓存: userinfo里面有用户名/密码/用户id （地址和contact直接从数据库读取就好）
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        //2、取出数据 用户id和用户名
        userID = preferences.getLong("userid",0);
        username=preferences.getString("username", null);



        //get database reference
        mDatabaseUserShoppingHistory = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("shopping history");
//        mDatabaseUserCart = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("cart items");

        /**
         * Get total number of cart items
         */
//
//        mDatabaseUserCart.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    cartItemsAmount = (dataSnapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        /**
         * Get total number of favorite items
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

    }

    private void initView() {

        setUpToolbar();
        mItemImage = findViewById(R.id.payment2_image);
        mItemName = findViewById(R.id.payment2_name);
        mItemPrice = findViewById(R.id.payment2_price);
        mItemDescription = findViewById(R.id.payment2_description);
//        mItemInfoAddCart = findViewById(R.id.item_info_add_cart);
        mItemInfoAddShoppingHistory = findViewById(R.id.payment);
    }


}