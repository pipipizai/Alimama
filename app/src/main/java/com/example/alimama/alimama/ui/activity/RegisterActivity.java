package com.example.alimama.alimama.ui.activity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

                //1 - Create child in root object
                //2 - Assign some value to the child object

                //fill in the information to the User class
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                //Push value by using database reference
                userRef.child("Users").child(username).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, "User created failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });






//                if (password == rePassword){
//                    mAuth.createUserWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    String TAG = "Show message";
//                                    if (task.isSuccessful()) {
//                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "createUserWithEmail:success");
//                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        Toast.makeText(RegisterActivity.this, "Authentication success.",
//                                                Toast.LENGTH_SHORT).show();
//                                        //updateUI(user);
//                                    } else {
//                                        // If sign in fails, display a message to the user.
//                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
//                                                Toast.LENGTH_SHORT).show();
//                                        //updateUI(null);
//                                    }

                                    // ...
//                                }
//                            });
//                }
//                if(password != rePassword){
//                    Toast.makeText(RegisterActivity.this, "Password and Repassword should be same",
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private void initView() {
        mEtUsername = (EditText)findViewById(R.id.register_edt_username);
        mEtPassword = (EditText)findViewById(R.id.register_edt_password);
        mEtRepassword = (EditText)findViewById(R.id.register_edt_repassword);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
//        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference();
    }

}
