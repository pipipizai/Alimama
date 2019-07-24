package com.example.alimama.alimama.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;
import android.graphics.Typeface;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.entity.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends BaseActvity {

    public static final int GALLERY_REQUEST = 1;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtRepassword;
    private EditText mEtPhone;
    private EditText mEtAddress;
    private Button mBtnRegister;
//    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private StorageReference mStorage;
    private Uri mImageUri = null;
    private User user;
    long totlaNumberUsers=0;
    private ImageButton mUserIcon;
    private String phone;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

     //   setUpToolbar();
        initView();
        initEvent();
        setUpRegisterToolbar();

        //字体
        TextView textView1 = findViewById(R.id.register_edt_username);
        TextView textView2 = findViewById(R.id.register_edt_password);
        TextView textView3 = findViewById(R.id.register_edt_repassword);
        TextView textView4 = findViewById(R.id.btn_register);
        Typeface tf1= Typeface.createFromAsset(getAssets(), "PTSans-Regular.ttf");
        textView1.setTypeface(tf1);
        textView2.setTypeface(tf1);
        textView3.setTypeface(tf1);
        textView4.setTypeface(tf1);

       // setTitle("Register");


    }

    private void initEvent() {
        mUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

        mBtnRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (v.getId() == R.id.btn_register) {
                            mBtnRegister.setScaleX((float) 0.9);
                            mBtnRegister.setScaleY((float) 0.9);
                        }
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        if (v.getId() == R.id.btn_register) {
                            mBtnRegister.setScaleX(1);
                            mBtnRegister.setScaleY(1);
                        }
                        phone = mEtPhone.getText().toString();
                        address = mEtAddress.getText().toString();
                        if (mImageUri == null) {
                            Toast.makeText(RegisterActivity.this, "Please add profile image", Toast.LENGTH_LONG).show();
                        } else if (phone == null) {
                            Toast.makeText(RegisterActivity.this, "Please add phone", Toast.LENGTH_LONG).show();
                        } else if (address == null) {
                            Toast.makeText(RegisterActivity.this, "Please add address", Toast.LENGTH_LONG).show();
                        } else {
                            userRegister();
                        }
                        break;
                    }
                }
                return false;
            }
        });
        }

    public void userRegister(){

        final String username = mEtUsername.getText().toString();
        final String password = mEtPassword.getText().toString();
        String rePassword = mEtRepassword.getText().toString();

        final StorageReference filePath = mStorage.child("User_Icons").child(mImageUri.getLastPathSegment());
        final UploadTask uploadTask = filePath.putFile(mImageUri);




                if(password.equals(rePassword)){

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Handle unsuccessful uploads
                            Toast.makeText(RegisterActivity.this, "Publish Failed!", Toast.LENGTH_LONG).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Uri downloadUrl = taskSnapshot.getUploadSessionUri();

                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return filePath.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();

                                        //fill in the information to the User class
                                        user = new User();
                                        user.setUsername(username);
                                        user.setPassword(password);
                                  //      user.setId(totlaNumberUsers+1);
                                        user.setIcon(downloadUri.toString());
                                        user.setAddress(address);
                                        user.setPhone(phone);

                                        userRef.child(username).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_LONG).show();
                                                    toLoginActivity();
                                                    finish();
                                                }else{
                                                    Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    } else {
                                        // Handle failures
                                        // ...
                                        Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                        }
                    });

        }else{
            Toast.makeText(RegisterActivity.this, "Repassword should equal to password ", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST&&data.getData()!=null) {

            mImageUri = data.getData();

            mUserIcon.setImageURI(mImageUri);
        }
    }



    private void initView() {
        mEtUsername = findViewById(R.id.register_edt_username);
        mEtPassword = findViewById(R.id.register_edt_password);
        mEtRepassword = findViewById(R.id.register_edt_repassword);
        mEtPhone = findViewById(R.id.register_phone);
        mEtAddress = findViewById(R.id.register_address);
        mBtnRegister = findViewById(R.id.btn_register);
        mUserIcon = findViewById(R.id.btn_user_icon);
//        mAuth = FirebaseAuth.getInstance();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mStorage = FirebaseStorage.getInstance().getReference();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    //get total number of users in database
                    totlaNumberUsers = (dataSnapshot.getChildrenCount());
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
