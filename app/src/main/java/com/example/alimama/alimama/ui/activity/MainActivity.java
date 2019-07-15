package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.fragment.CartFragment;
import com.example.alimama.alimama.fragment.CategoriesFragment;
import com.example.alimama.alimama.fragment.DiscoverFragment;
import com.example.alimama.alimama.fragment.MainFragment;
import com.example.alimama.alimama.fragment.MeFragment;
import com.example.alimama.alimama.fragment.MessageFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    protected LinearLayout mMenuHome;
    protected LinearLayout mMenuCategories;
    protected LinearLayout mMenuDiscover;
    protected LinearLayout mMenuCart;
    protected LinearLayout mMenuMe;
    protected LinearLayout mMenuPublish;
    protected MainFragment mMainFragment = new MainFragment();//HOME
    protected CategoriesFragment mCaregoriesFragment = new CategoriesFragment();//Categories
    protected DiscoverFragment mDiscoverFragment = new DiscoverFragment();//Discover
    protected CartFragment mCartFragment = new CartFragment();//Cart
    protected MessageFragment mMessageFragment = new MessageFragment();//Me
    protected MeFragment mMeFragment = new MeFragment();//Me



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取管理类
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_content, mMainFragment)
                .add(R.id.container_content, mCaregoriesFragment)
                .hide(mCaregoriesFragment)
                .add(R.id.container_content, mMessageFragment)
                .hide(mMessageFragment)
                .add(R.id.container_content,mMeFragment)
                .hide(mMeFragment)

                //事物添加 默认：显示首页 其他页面隐藏
                //提交
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!isLogin()){
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }else{
            initView();
        }

        mMenuPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPublishActivity();
            }
        });


    }

    private boolean isLogin() {

        boolean isLogin;
        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        String username=preferences.getString("username", null);
        String password=preferences.getString("password", null);

        if(username==null&&password==null){
            isLogin=false;
        }else {
            isLogin=true;
        }

        return isLogin;
    }

    /*
     *初始化视图
     */
    public void initView(){
        mMenuHome = (LinearLayout) this.findViewById(R.id.menu_home);
        mMenuCategories = (LinearLayout) this.findViewById(R.id.menu_categories);
//        mMenuDiscover = (LinearLayout) this.findViewById(R.id.menu_discover);
        mMenuCart = (LinearLayout) this.findViewById(R.id.menu_cart);
        mMenuMe = (LinearLayout) this.findViewById(R.id.menu_me);
        mMenuPublish = (LinearLayout)findViewById(R.id.menu_publish);

        mMenuHome.setOnClickListener(this);
        mMenuCategories.setOnClickListener(this);
//        mMenuDiscover.setOnClickListener(this);
        mMenuCart.setOnClickListener(this);
        mMenuMe.setOnClickListener(this);
        mMenuPublish.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_home: //首页
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(mMainFragment)
                        .hide(mCaregoriesFragment)
                        .hide(mMessageFragment)
                        .hide(mMeFragment)
                        .commit();
                break;
            case R.id.menu_categories: //分类
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainFragment)
                        .show(mCaregoriesFragment)
                        .hide(mMessageFragment)
                        .hide(mMeFragment)
                        .commit();
                break;
//            case R.id.menu_discover: //发现
//                this.getSupportFragmentManager()
//                        .beginTransaction()
//                        .hide(mMainFragment)
//                        .hide(mCaregoriesFragment)
//                        .show(mDiscoverFragment)
//                        .hide(mCartFragment)
//                        .hide(mMeFragment)
//                        .commit();
//                break;

//            case R.id.menu_publish: //publish item
//                toPublishActivity();
//                this.getSupportFragmentManager()
//                        .beginTransaction()
//                        .show(mMainFragment)
//                        .hide(mCaregoriesFragment)
//                        .hide(mCartFragment)
//                        .hide(mMeFragment)
//                        .commit();
//                break;


            case R.id.menu_cart: //购物车
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainFragment)
                        .hide(mCaregoriesFragment)
//                        .hide(mDiscoverFragment)
                        .show(mMessageFragment)
                        .hide(mMeFragment)
                        .commit();
                break;
            case R.id.menu_me: //我
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainFragment)
                        .hide(mCaregoriesFragment)
//                        .hide(mDiscoverFragment)
                        .hide(mMessageFragment)
                        .show(mMeFragment)
                        .commit();
                break;
        }
    }

    private void toPublishActivity() {
        Intent intent = new Intent(this,PublishActivity.class);
        startActivity(intent);
    }
}

//