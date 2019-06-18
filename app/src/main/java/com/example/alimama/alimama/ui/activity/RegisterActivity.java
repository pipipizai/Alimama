package com.example.alimama.alimama.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends BaseActvity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtRepassword;
    private Button mBtnRegister;
//    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private User user;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setUpToolbar();
        initView();
        initEvent();

        setTitle("Register");


    }



    private void initEvent() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                String rePassword = mEtRepassword.getText().toString();







                if(password.equals(rePassword)){


                    //fill in the information to the User class
                    user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setId(maxid+1);

                    userRef.child(String.valueOf(maxid+1)).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                                toLoginActivity();
                            }else{
                                Toast.makeText(RegisterActivity.this, "User created failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(RegisterActivity.this, "Password should be same as repassword", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void initView() {
        mEtUsername = (EditText)findViewById(R.id.register_edt_username);
        mEtPassword = (EditText)findViewById(R.id.register_edt_password);
        mEtRepassword = (EditText)findViewById(R.id.register_edt_repassword);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
//        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}
