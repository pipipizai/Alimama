package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.Item;
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

import java.util.HashMap;
import java.util.Map;

public class PublishActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST = 1;

    private ImageButton mSelectImageButton;
    private EditText mPublishItemPrice;
    private EditText mPublishItemName;
    private EditText mPublishItemDscription;
    private Button mPublishButton;

    private Uri mImageUri = null;

    private Item item;

    private StorageReference mStorage;
    private DatabaseReference mDatabaseItems;
    private DatabaseReference mDatabaseUsers;

    private long itemID;
    private long publishedItemNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        initView();
        initEvent();
    }

    private void initEvent() {
        mSelectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

        mPublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPublish();
            }
        });

    }

    private void initView() {

        mSelectImageButton = (ImageButton) findViewById(R.id.btn_publish_image);
        mPublishItemPrice = (EditText) findViewById(R.id.publish_item_price);
        mPublishItemName = (EditText) findViewById(R.id.publish_item_name);
        mPublishItemDscription = (EditText) findViewById(R.id.publish_item_description);
        mPublishButton = (Button) findViewById(R.id.btn_publish);

        mStorage = FirebaseStorage.getInstance().getReference();


    }

    private void startPublish() {

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        final long userID = preferences.getLong("userid",0);

        final String price_value = mPublishItemPrice.getText().toString().trim();
        final String name_value = mPublishItemName.getText().toString().trim();
        final String description_value = mPublishItemDscription.getText().toString().trim();

        mDatabaseItems = FirebaseDatabase.getInstance().getReference().child("Items");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(String.valueOf(userID)).child("published items");

        mDatabaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    itemID = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    publishedItemNumber = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (!TextUtils.isEmpty(price_value) && !TextUtils.isEmpty(name_value)
                && !TextUtils.isEmpty(description_value) && mImageUri != null) {

            final StorageReference filePath = mStorage.child("Item_Images").child(mImageUri.getLastPathSegment());



            final UploadTask uploadTask = filePath.putFile(mImageUri);
            // filePath.putFile(mImageUri).addOnFailureListener(new OnFailureListener() {
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    // Handle unsuccessful uploads
                    Toast.makeText(PublishActivity.this, "Publish Failed!", Toast.LENGTH_LONG).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();

//                    taskSnapshot.getUploadSessionUri();
////                    Task<Uri> task = filePath.getDownloadUrl();
////                    Uri downloadUrl = task.getResult();

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
//                                user = new User();
//                                user.setUsername(username);
//                                user.setPassword(password);
//                                user.setId(maxid+1);
//
//                                userRef.child(String.valueOf(maxid+1)).setValue(user).addOnCompleteListener

                                  item = new Item();
                                  item.setName(name_value);
                                  item.setPrice(price_value);
                                  item.setDescription(description_value);
                                  item.setImage(downloadUri.toString());
                                  item.setUserID(userID);

                                  mDatabaseItems.child(String.valueOf(itemID+1)).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            mDatabaseUsers.child(String.valueOf("item id "+(publishedItemNumber+1))).setValue(itemID);

                                            Toast.makeText(PublishActivity.this, "Successfully publish!", Toast.LENGTH_LONG).show();

                                            toMainActivity();
                                        }else{
                                            Toast.makeText(PublishActivity.this, "Publishfailed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

//                                DatabaseReference newPost = mDatabaseItems.push();
//
//                                Map<String, Object> map = new HashMap<>();
//                                map.put("name", name_value);
//                                map.put("price", price_value);
//                                map.put("description", description_value);
//                                //map.put("image", downloadUrl.toString());
//                                map.put("image", downloadUri.toString());
//                                map.put("userID",userID);
//
//                                newPost.setValue(map);

//                                mDatabaseUsers.child(String.valueOf(userID)).child("Items").setValue(itemID+1);
//
//                                Toast.makeText(PublishActivity.this, "Successfully publish!", Toast.LENGTH_LONG).show();

                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });


//                    DatabaseReference newPost = mDatabase.push();

//                    newPost.child("Item Price").setValue(price_value);
////                    newPost.child("Item Name").setValue(name_value);
////                    newPost.child("Item Description").setValue(description_value);
////                    newPost.child("Item Image").setValue(downloadUrl.toString());

//                    Map<String, Object> map = new HashMap<>();
//                    map.put("name", name_value);
//                    map.put("price", price_value);
//                    map.put("description", description_value);

                    //map.put("image", downloadUrl.toString());

//                    map.put("image", urlTask.toString());
//
//                    newPost.setValue(map);

                    Toast.makeText(PublishActivity.this, "Successfully publish!", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(PublishActivity.this, MainActivity.class));

                }
            });

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST) {

            mImageUri = data.getData();

            mSelectImageButton.setImageURI(mImageUri);

        }
    }

    private void toMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
