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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemInformationActivity extends BaseActvity {

//    public static Object ExtraData;

    private TextView mItemID;
    private TextView mItemPublishedUserName;
    private ImageView mItemImage;
    private ImageView mPublishedUserProfile;
    private TextView mItemName;
    private TextView mItemPrice;
    private TextView mItemDescription;
    private ImageButton mItemInfoAddFavotie;
    private ImageButton mItemInfoAddCart;
    private ImageButton mUserContactButton;
    private Button mItemInfoPayment;


    private long itemID;
    private String itemPublishedUserName;
    private String ItemIDString;
    private String ItemImage;
    private String itemName;
    private String itemPrice;
    private String itemDescription;
    private String userProfileImage;

    private DatabaseReference mDatabaseUserCart;
    private DatabaseReference mDatabaseUserFavorite;
    private DatabaseReference mDatabaseItems;
    private DatabaseReference mDatabaseUserPayment;

    private long cartItemsAmount=0;
    private long favoriteItemsAmount=0;
    private long userID=0;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);

        initView();

        getItemInformation();

        displayItemInfomation();

        setUpToolbar();
        setTitle("Item Info");

        //字体
        TextView textView1 = findViewById(R.id.txt_add_favorite);
        TextView textView2 = findViewById(R.id.txt_add_cart);
        TextView textView3 = findViewById(R.id.conncet_seller);
        TextView textView4 = findViewById(R.id.item_info_payment);
        TextView textView5 = findViewById(R.id.item_info_price);
        TextView textView6 = findViewById(R.id.item_info_Name);
        TextView textView7 = findViewById(R.id.item_info_description);
        TextView textView8 = findViewById(R.id.txt_guess_you_like);

        Typeface tf1= Typeface.createFromAsset(getAssets(), "PTSans-Regular.ttf");
        textView1.setTypeface(tf1);
        textView2.setTypeface(tf1);
        textView3.setTypeface(tf1);
        textView4.setTypeface(tf1);
        textView5.setTypeface(tf1);
        textView6.setTypeface(tf1);
        textView7.setTypeface(tf1);
        textView8.setTypeface(tf1);

        //create a item object
        final Item item = new Item();
        //set value in this item object(more detail look into Class Item)
        item.setName(itemName);
        item.setPrice(itemPrice);
        item.setDescription(itemDescription);
        item.setImage(ItemImage);
        item.setUserName(itemPublishedUserName);
        item.setItemID(itemID);
        item.setUserProfileImage(userProfileImage);

        /**
         * add item to my favotite
         */
        mItemInfoAddFavotie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这两个语句你可以分别屏蔽运行程序看看是什么效果

                //这是把itemid（不是对象）放进数据库
//               mDatabaseUserFavorite.child(String.valueOf(itemID)).setValue(itemID);

                //这是把整个item对象放进数据库
                if(itemPublishedUserName.equals(username)) {
                    Toast.makeText(ItemInformationActivity.this, "Can not save your item", Toast.LENGTH_LONG).show();
                }else {
                    mDatabaseUserFavorite.child(String.valueOf(itemID)).setValue(item);
                }
            }
        });

        /**
         * pay and add item to sale record
         */
//        mItemInfoPayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //这两个语句你可以分别屏蔽运行程序看看是什么效果
//
//                //这是把itemid（不是对象）放进数据库
////               mDatabaseUserFavorite.child(String.valueOf(itemID)).setValue(itemID);
//
//                //这是把整个item对象放进数据库
//                if(itemPublishedUserName.equals(username)) {
//                    Toast.makeText(ItemInformationActivity.this, "Can not save your item", Toast.LENGTH_LONG).show();
//                }else {
//                    mDatabaseUserFavorite.child(String.valueOf(itemID)).setValue(item);
//                }
//            }
//        });


        /**
         * add item to my cart
         */
//        mItemInfoAddCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //mDatabaseUserCart.child(String.valueOf("item "+(cartItemsNumber +1)+" id ")).setValue(itemID);
//                mDatabaseUserCart.child(String.valueOf(itemID)).setValue(item);
//            }
//        });

        /**
         * chat with seller
         */
        mUserContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(itemPublishedUserName.equals(username)) {
                    Toast.makeText(ItemInformationActivity.this, "You can not talk to yourself", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(ItemInformationActivity.this, MessageActivity.class);
                    intent.putExtra("itemPublishedUserName", itemPublishedUserName);
                    intent.putExtra("username", username);
                    startActivity(intent);

                }
            }
        });

        /**
         * payment
         */
        mItemInfoPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(itemPublishedUserName.equals(username)) {
                    Toast.makeText(ItemInformationActivity.this, "Can not buy yourself item", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(ItemInformationActivity.this, PaymentActivity.class);
                    intent.putExtra("itemID", itemID);
                    intent.putExtra("itemName", itemName);
                    intent.putExtra("itemPrice", itemPrice);
                    intent.putExtra("ItemImage", ItemImage);
                    intent.putExtra("itemDescription", itemDescription);
                    intent.putExtra("itemPublishedUserName", itemPublishedUserName);
                    intent.putExtra("userProfileImage",userProfileImage);

                    startActivity(intent);
                }
            }
        });
    }



   private void toMessageActivity() {


    }

    private void displayItemInfomation() {

        Glide.with(this).load(ItemImage).placeholder(R.drawable.default_item_image).into(mItemImage);
        Glide.with(this).load(userProfileImage).placeholder(R.drawable.default_item_image).into(mPublishedUserProfile);
       // mItemName.setText(itemName);
        mItemPrice.setText(itemPrice);
        mItemDescription.setText(itemDescription);
        mItemPublishedUserName.setText(itemPublishedUserName);


    }

    private void getItemInformation() {

        //get the transffered data
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){ //防止直接启动MainActivity时空指针闪退

            itemID = bundle.getLong("item id");
            ItemIDString = String.valueOf(itemID);
        }

        ItemImage=bundle.getString("image");
        itemName=bundle.getString("name");
        itemPrice=bundle.getString("price");
        itemDescription=bundle.getString("description");
        itemPublishedUserName=bundle.getString("itemPublishedUserName");
        userProfileImage= bundle.getString("userProfileImage");

        //1、获取Preferences
        // 相当于本地缓存: userinfo里面有用户名/密码/用户id （地址和contact直接从数据库读取就好）
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        //2、取出数据 用户id和用户名
        userID = preferences.getLong("userid",0);
        username=preferences.getString("username", null);

        //get database reference
        mDatabaseUserFavorite = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("favorite items");
        mDatabaseUserCart = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("cart items");
//        mDatabaseUserPayment = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("shopping history");

        /**
         * Get total number of cart items
         */
        mDatabaseUserCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    cartItemsAmount = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /**
         * Get total number of favorite items
         */
        mDatabaseUserFavorite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    favoriteItemsAmount = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initView() {

        setUpToolbar();
        mItemImage = findViewById(R.id.item_info_image);
      //  mItemName = findViewById(R.id.item_info_Name);
        mItemPrice = findViewById(R.id.item_info_price);
        mItemDescription = findViewById(R.id.item_info_description);
        mItemInfoAddCart = findViewById(R.id.item_info_add_cart);
        mItemInfoAddFavotie = findViewById(R.id.item_info_add_favorite);
        mUserContactButton = findViewById(R.id.image_contact_user);
        mItemInfoPayment = findViewById(R.id.item_info_payment);
        mPublishedUserProfile = findViewById(R.id.item_info_published_user_profile);
        mItemPublishedUserName = findViewById(R.id.item_info_published_user_name);

    }


}
