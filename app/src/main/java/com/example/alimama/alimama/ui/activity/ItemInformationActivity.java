package com.example.alimama.alimama.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemInformationActivity extends BaseActvity {

//    public static Object ExtraData;

    private TextView mItemID;
    private ImageView mItemImage;
    private TextView mItemName;
    private TextView mItemPrice;
    private TextView mItemDescription;

    private long itemID;
    private String ItemIDString;
    private String ItemImage;
    private String itemName;
    private String itemPrice;
    private String itemDescription;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);

        initView();


        getItemInformation();

        displayItemInfo();


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
    }

    private void initView() {

        setUpToolbar();
        mItemImage = findViewById(R.id.item_info_image);
        mItemName = findViewById(R.id.item_info_Name);
        mItemPrice = findViewById(R.id.item_info_price);
        mItemDescription = findViewById(R.id.item_info_description);
    }


}
