package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Typeface;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private DatabaseReference loginRef;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        
        initEvent();

        //字体
        TextView textView1 = findViewById(R.id.login_head);
        TextView textView2 = findViewById(R.id.edt_uesrname);
        TextView textView3 = findViewById(R.id.edt_password);
        TextView textView4 = findViewById(R.id.btn_login);
        TextView textView5 = findViewById(R.id.btn_register);
        Typeface tf1= Typeface.createFromAsset(getAssets(), "againts.otf");
        Typeface tf2= Typeface.createFromAsset(getAssets(), "dry_brush.ttf");
        textView1.setTypeface(tf1);
        textView2.setTypeface(tf2);
        textView3.setTypeface(tf2);
        textView4.setTypeface(tf1);
        textView5.setTypeface(tf1);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void initEvent() {


        clearSharedPreferences();

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 登录成功？

                final String username = mEtUsername.getText().toString();
                final String password = mEtPassword.getText().toString();


//                loginRef = FirebaseDatabase.getInstance().getReference().child("Users");

                if(loginRef.child(username)!=null){
                    loginRef.child(username).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            long userid = user.getId();

                            if (password.equals(user.getPassword())){

                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();

                                //1、打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences
                                SharedPreferences preferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);

                                //2、让setting处于编辑状态
                                SharedPreferences.Editor editor=preferences.edit();

                                //3、存放数据
                                editor.putString("username", username);
                                editor.putString("password", password);
                                editor.putLong("userid",userid);

                                //4、完成提交
                                editor.commit();

                                toMainActivity();
                            } else {

                                Toast.makeText(LoginActivity.this, "The password is incorrect", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterActivity();
            }
        });
    }

    private void clearSharedPreferences() {

        //1、打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences
        SharedPreferences preferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、让setting处于编辑状态
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        //4、完成提交
        editor.commit();
    }

    private void toRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void toMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        mEtUsername = findViewById(R.id.edt_uesrname);
        mEtPassword = findViewById(R.id.edt_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnRegister = findViewById(R.id.btn_register);

        loginRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

}
