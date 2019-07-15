package com.example.alimama.alimama.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alimama.alimama.R;
import com.google.firebase.database.FirebaseDatabase;

public class MessageFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

//        //字体
//        TextView textView1 = getView().findViewById(R.id.cart_head);
//        Typeface tf= Typeface.createFromAsset(getContext().getAssets(), "againts.otf");
//        textView1.setTypeface(tf);

    }

    public void initView() {

//        mCartList = getView().findViewById(R.id.cart_items);
//        mCartList.setHasFixedSize(true);
//        mCartList.setLayoutManager(new LinearLayoutManager(getContext()));

//        //1、获取Preferences
//        SharedPreferences preferences=getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
//        //2、取出数据
//        userID = preferences.getLong("userid",0);
//        username=preferences.getString("username", null);
//
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("cart items");

    }
}
