package com.example.alimama.alimama.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.alimama.alimama.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

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

        final String price_value = mPublishItemPrice.getText().toString().trim();
        final String name_value = mPublishItemName.getText().toString().trim();
        final String description_value = mPublishItemDscription.getText().toString().trim();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");

        if (!TextUtils.isEmpty(price_value) && !TextUtils.isEmpty(name_value)
                && !TextUtils.isEmpty(description_value) && mImageUri != null) {

            StorageReference filePath = mStorage.child("Item_Images").child(mImageUri.getLastPathSegment());

            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();

                    DatabaseReference newPost = mDatabase.push();

//                    newPost.child("Item Price").setValue(price_value);
////                    newPost.child("Item Name").setValue(name_value);
////                    newPost.child("Item Description").setValue(description_value);
////                    newPost.child("Item Image").setValue(downloadUrl.toString());

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name_value);
                    map.put("price", price_value);
                    map.put("description", description_value);
                    map.put("image", downloadUrl.toString());

                    newPost.setValue(map);

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


}
