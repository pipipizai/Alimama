package com.example.alimama.alimama.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alimama.alimama.R;

public class SplashActivity extends AppCompatActivity {

    private Button mBtnSkip;
    private Handler mHandler = new Handler();

    private Runnable mRunnableToLogin = new Runnable(){
        @Override
        public void run() {
            toLoginActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initEvent();

        mHandler.postDelayed(mRunnableToLogin,3000);
    }

    private void initEvent() {
        mBtnSkip.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mRunnableToLogin);
                toLoginActivity();
            }
        });

    }

    private void initView(){
        mBtnSkip = (Button) findViewById(R.id.id_btn_skip);
    }

    private void toLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacks(mRunnableToLogin);
    }
}
