package com.example.alimama.alimama.ui.activity;


//yexuangaile
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.fragment.DiscoverFragment;
import com.example.alimama.alimama.fragment.HomeFragment;
import com.example.alimama.alimama.fragment.MeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    protected LinearLayout mMenueHome;
    protected LinearLayout mMenueDiscover;
    protected LinearLayout mMenueMe;
    protected HomeFragment mHomeFragment = new HomeFragment();//HOME
    protected DiscoverFragment mDiscoverFragment = new DiscoverFragment();//Discover
    protected MeFragment mMeFragment = new MeFragment();//Me

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        //获取管理类
        this.getSupportFragmentManager()
                    .beginTransaction()
                .add(R.id.container_content,mHomeFragment)
                .add(R.id.container_content,mDiscoverFragment)
                    .hide(mDiscoverFragment)
                .add(R.id.container_content,mMeFragment)
                    .hide(mMeFragment)

        //事物添加 默认：显示首页 其他页面隐藏
        //提交
        .commit();
    }

    /*
     *初始化视图
     */
    public void initView(){
        mMenueHome = (LinearLayout)this.findViewById(R.id.menu_home);
        mMenueDiscover = (LinearLayout)this.findViewById(R.id.menu_discover);
        mMenueMe = (LinearLayout)this.findViewById(R.id.menu_me);

        mMenueHome.setOnClickListener(this);
        mMenueDiscover.setOnClickListener(this);
        mMenueMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_home: //首页
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(mHomeFragment)
                        .hide(mDiscoverFragment)
                        .hide(mMeFragment)
                        .commit();
                break;
            case R.id.menu_discover: //发现
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mHomeFragment)
                        .show(mDiscoverFragment)
                        .hide(mMeFragment)
                        .commit();
                break;
            case R.id.menu_me: //我
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mHomeFragment)
                        .hide(mDiscoverFragment)
                        .show(mMeFragment)
                        .commit();
                break;
        }
    }
}
