package com.example.alimama.alimama.ui.activity;

import android.content.Context;
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
    private ImageView mItemImage;
    private TextView mItemName;
    private TextView mItemPrice;
    private TextView mItemDescription;
    private Button mItemInfoAddFavotie;
    private LinearLayout mItemInfoAddCart;


    private long itemID;
    private long itemPublishedUserID;
    private String ItemIDString;
    private String ItemImage;
    private String itemName;
    private String itemPrice;
    private String itemDescription;

    private DatabaseReference mDatabaseUserCart;
    private DatabaseReference mDatabaseUserFavorite;
    private DatabaseReference mDatabaseItems;

    private long cartItemsNumber=0;
    private long favoriteItemsNumber=0;
    private long userID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);

        initView();

        getItemInformation();

        displayItemInfo();

        final Item item = new Item();
        item.setName(itemName);
        item.setPrice(itemPrice);
        item.setDescription(itemDescription);
        item.setImage(ItemImage);
        item.setUserID(itemPublishedUserID);
        item.setItemID(itemID);

        mItemInfoAddFavotie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    mDatabaseUserFavorite.child(String.valueOf("item "+(favoriteItemsNumber +1)+" id ")).setValue(itemID);
                mDatabaseUserFavorite.child(String.valueOf("item "+(favoriteItemsNumber +1)+" id ")).setValue(item);
            }
        });

        mItemInfoAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mDatabaseUserCart.child(String.valueOf("item "+(cartItemsNumber +1)+" id ")).setValue(itemID);
                mDatabaseUserCart.child(String.valueOf("item "+(cartItemsNumber +1)+" id ")).setValue(item);
            }
        });



    }

    private void displayItemInfo() {

        Glide.with(this).load(ItemImage).placeholder(R.drawable.default_item_image).into(mItemImage);
        mItemName.setText(itemName);
        mItemPrice.setText(itemPrice);
        mItemDescription.setText(itemDescription);
    }

    private void getItemInformation() {

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){ //防止直接启动MainActivity时空指针闪退

            itemID = bundle.getLong("item id");
            ItemIDString = String.valueOf(itemID);
        }

        ItemImage=bundle.getString("image");
        itemName=bundle.getString("name");
        itemPrice=bundle.getString("price");
        itemDescription=bundle.getString("description");
        itemPublishedUserID=bundle.getLong("itemPublishedUserID");

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        userID = preferences.getLong("userid",0);

        mDatabaseUserFavorite = FirebaseDatabase.getInstance().getReference().child("Users").child(String.valueOf(userID)).child("favorite items");
        mDatabaseUserCart = FirebaseDatabase.getInstance().getReference().child("Users").child(String.valueOf(userID)).child("cart items");

        mDatabaseUserCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    cartItemsNumber = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabaseUserFavorite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    favoriteItemsNumber = (dataSnapshot.getChildrenCount());
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
        mItemName = findViewById(R.id.item_info_Name);
        mItemPrice = findViewById(R.id.item_info_price);
        mItemDescription = findViewById(R.id.item_info_description);
        mItemInfoAddCart = findViewById(R.id.item_info_add_cart);
        mItemInfoAddFavotie = findViewById(R.id.item_info_add_favorite);
    }


}
