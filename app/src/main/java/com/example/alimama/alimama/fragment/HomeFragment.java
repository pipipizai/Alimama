package com.example.alimama.alimama.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.ui.activity.PublishActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by LING on 6/5/2019.
 * 主界面视图 - HOME
 */

public class HomeFragment extends Fragment{

    public static final int GALLERY_INTENT = 2;
    private Button mPublishButton;

    private StorageReference mStorage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPublishButton = (Button)getView().findViewById(R.id.home_publish);
        mStorage = FirebaseStorage.getInstance().getReference();
        mPublishButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                Intent publish = new Intent(getActivity(), PublishActivity.class);
                startActivity(publish);


            }
        });

    }






}
