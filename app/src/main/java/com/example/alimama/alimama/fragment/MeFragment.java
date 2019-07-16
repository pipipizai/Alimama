package com.example.alimama.alimama.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Typeface;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.bean.User;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.ui.activity.FavoriteActivity;
import com.example.alimama.alimama.ui.activity.LoginActivity;
import com.example.alimama.alimama.ui.activity.MeInfoActivity;
import com.example.alimama.alimama.ui.activity.MyPublishActivity;
import com.example.alimama.alimama.ui.activity.ShoppingHistoryActivity;
import com.example.alimama.alimama.ui.activity.ShoppingHistoryActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by LING on 6/5/2019.
 */

public class MeFragment extends Fragment {

    protected Button mBtnLogin;
    protected TextView mTextMeInfo;
    protected TextView mTextFavorite;
    protected TextView mTextShoppingHistory;
    protected TextView mTextPublish;
    protected TextView mTextLogOut;
    protected TextView mTextShoppingCart;
    protected TextView mTextItemSold;
    private ImageView mUserIcon;
    private long userID=0;
    private String username;
    private String userIcon;

    private DatabaseReference mDatabaseRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        //toLoginActivity
//        mBtnLogin = (Button)getView().findViewById(R.id.btn_login);
//        mBtnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //登录
//                Intent login = new Intent(getActivity(), LoginActivity.class);
//                startActivity(login);
//            }
//        });

        //字体
        TextView textView1 = getView().findViewById(R.id.txt_me_fav);
        TextView textView2 = getView().findViewById(R.id.txt_me_pub);
        TextView textView3 = getView().findViewById(R.id.txt_me_info);
        TextView textView4 = getView().findViewById(R.id.txt_me_log_out);
        TextView textView5 = getView().findViewById(R.id.txt_me_rec);
        TextView textView6 = getView().findViewById(R.id.txt_shopping_cart);
//        TextView textView7 = getView().findViewById(R.id.txt_me_sold);
        Typeface tf= Typeface.createFromAsset(getContext().getAssets(), "againts.otf");
        textView1.setTypeface(tf);
        textView2.setTypeface(tf);
        textView3.setTypeface(tf);
        textView4.setTypeface(tf);
        textView5.setTypeface(tf);
        textView6.setTypeface(tf);
//        textView7.setTypeface(tf);

        //1、获取Preferences
        SharedPreferences preferences=getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        userID = preferences.getLong("userid",0);
        username=preferences.getString("username", null);
        userIcon=preferences.getString("userIcon", null);


        mUserIcon = getView().findViewById(R.id.me_icon);

        displayUserIcon();


        //toMeInfoActivity
        mTextMeInfo = getView().findViewById(R.id.txt_me_info);
        mTextMeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent meInfo = new Intent(getActivity(), MeInfoActivity.class);
                startActivity(meInfo);
            }
        });


        //toShoppingCartActivity
        mTextMeInfo = getView().findViewById(R.id.txt_shopping_cart);
        mTextMeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent meInfo = new Intent(getActivity(), ShoppingHistoryActivity.class);
                startActivity(meInfo);
            }
        });


//        //toItemSoldActivity
//        mTextMeInfo = getView().findViewById(R.id.txt_me_info);
//        mTextMeInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent meInfo = new Intent(getActivity(), MeInfoActivity.class);
//                startActivity(meInfo);
//            }
//        });

        //toFavoriteActivity
        mTextFavorite = getView().findViewById(R.id.txt_me_fav);
        mTextFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent favorite = new Intent(getActivity(), FavoriteActivity.class);
                startActivity(favorite);
            }
        });

        //toMypublishActivity
        mTextPublish = getView().findViewById(R.id.txt_me_pub);
        mTextPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent publish = new Intent(getActivity(), MyPublishActivity.class);
                startActivity(publish);
            }
        });

        //toShoppingHistoryActivity
        mTextShoppingHistory = getView().findViewById(R.id.txt_me_rec);
        mTextShoppingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shoppinghistory = new Intent(getActivity(), ShoppingHistoryActivity.class);
                startActivity(shoppinghistory);
            }
        });

        //toLogOutActivity
        mTextLogOut =  getView().findViewById(R.id.txt_me_log_out);
        mTextLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent logout = new Intent(getActivity(), LoginActivity.class);
                startActivity(logout);
            }
        });

    }





    public void displayUserIcon(){


        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(username);

        Glide.with(this).load(userIcon).placeholder(R.drawable.default_item_image).into(mUserIcon);

    }
}
