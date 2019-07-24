package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;
import android.graphics.Typeface;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.entity.CategoriesName;
import com.example.alimama.alimama.entity.Item;
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

import java.util.ArrayList;
import java.util.List;

public class PublishActivity extends BaseActvity {

    public static final int GALLERY_REQUEST = 1;

    private ImageButton mSelectImageButton;
    private EditText mPublishItemPrice;
    private EditText mPublishItemName;
    private EditText mPublishItemDscription;
    private TextView mPublishCategories;
    private Button mPublishButton;

    private Uri mImageUri = null;

    private Item item;

    private StorageReference mStorage;
    private DatabaseReference mDatabaseItems;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCategoriesName;
    private DatabaseReference mDatabaseCategories;

    private long itemsNumber;
   // private long publishedItemNumber;
    private long categoriesItemNumber;
    private List<String> categoriesList;
    private String catrgory=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        setUpToolbar();
        setTitle("Publish Item");

        initView();
        initEvent();

        TextView textView1 = findViewById(R.id.publish_item_name);
        TextView textView2 = findViewById(R.id.publish_item_price);
        TextView textView3 = findViewById(R.id.publish_item_description);
        TextView textView4 = findViewById(R.id.btn_publish);
        Typeface tf1= Typeface.createFromAsset(getAssets(), "PTSans-Regular.ttf");
        textView1.setTypeface(tf1);
        textView2.setTypeface(tf1);
        textView3.setTypeface(tf1);
        textView4.setTypeface(tf1);
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

        mPublishButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (v.getId() == R.id.btn_publish) {
                            mPublishButton.setScaleX((float) 0.9);
                            mPublishButton.setScaleY((float) 0.9);
                        }
                        break;


                    case MotionEvent.ACTION_UP:
                        if (v.getId() == R.id.btn_publish) {
                            mPublishButton.setScaleX(1);
                            mPublishButton.setScaleY(1);
                        }

                        startPublish();
                        break;

                }
                return false;
            }

        });
//
//            @Override
//            public void onClick(View view) {
//                startPublish();
//            }


        mPublishCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCatrgoryDialog();

            }

        });

    }

    private void selectCatrgoryDialog(){
        //List<String> categoriesList;
        //String[] categories =

        categoriesList = new ArrayList<>();

        mDatabaseCategoriesName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoriesList.clear();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                    CategoriesName categoriesName = snapshot.getValue(CategoriesName.class);

                    categoriesList.add(categoriesName.getCategoriesName());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,categoriesList);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                        .setTitle("Please select categoty")
                                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                catrgory = null;
                                                catrgory = categoriesList.get(which);
                                                mPublishCategories.setText(catrgory);
                                                Toast.makeText(PublishActivity.this, catrgory, Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            }
                                        });
        builder.show();


    }

    private void initView() {

        mSelectImageButton = (ImageButton) findViewById(R.id.btn_publish_image);
        mPublishItemPrice = (EditText) findViewById(R.id.publish_item_price);
        mPublishItemName = (EditText) findViewById(R.id.publish_item_name);
        mPublishItemDscription = (EditText) findViewById(R.id.publish_item_description);
        mPublishButton = (Button) findViewById(R.id.btn_publish);
        mPublishCategories = findViewById(R.id.publish_item_categories);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabaseCategoriesName = FirebaseDatabase.getInstance().getReference().child("CategoriesName");
        mDatabaseCategories = FirebaseDatabase.getInstance().getReference().child("Categories");


    }

    private void startPublish() {

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        final long userID = preferences.getLong("userid",0);
        final String username = preferences.getString("username",null);
        final String userIcon = preferences.getString("userIcon",null);
        final String price_value = mPublishItemPrice.getText().toString().trim();
        final String name_value = mPublishItemName.getText().toString().trim();
        final String description_value = mPublishItemDscription.getText().toString().trim();



        mDatabaseItems = FirebaseDatabase.getInstance().getReference().child("Items");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("published items");

        mDatabaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    itemsNumber = (dataSnapshot.getChildrenCount());
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

                                  item = new Item();
                                  item.setName(name_value);
                                  item.setPrice(price_value);
                                  item.setDescription(description_value);
                                  item.setImage(downloadUri.toString());
                                  item.setUserName(username);
                                  item.setItemID(itemsNumber+1);
                                  item.setUserProfileImage(userIcon);

                                mDatabaseCategories.child(catrgory).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            categoriesItemNumber = (dataSnapshot.getChildrenCount());
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                  mDatabaseItems.child(String.valueOf(itemsNumber+1)).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            mDatabaseUsers.child(String.valueOf(itemsNumber)).setValue(item);
                                            mDatabaseCategories.child(catrgory).child(String.valueOf(categoriesItemNumber+1)).setValue(item);
                                            Toast.makeText(PublishActivity.this, "Successfully publish!", Toast.LENGTH_LONG).show();

                                            toMainActivity();
                                        }else{
                                            Toast.makeText(PublishActivity.this, "Publishfailed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });



                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });


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