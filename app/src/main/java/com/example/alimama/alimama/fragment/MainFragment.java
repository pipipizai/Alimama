package com.example.alimama.alimama.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.adapter.MainHeaderAdAdapter;
import com.example.alimama.alimama.ui.activity.PublishActivity;
import com.example.alimama.alimama.util.DataUtil;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by LING on 6/5/2019.
 * 主界面视图 - HOME
 */

public class MainFragment extends Fragment {

    public static final int GALLERY_INTENT = 2;
    private Button mPublishButton;

    protected int[] icons = {R.mipmap.header_pic_ad1, R.mipmap.header_pic_ad2, R.mipmap.header_pic_ad1};
    protected ViewPager mVPagerHeaderAd;//主页头部广告

    private StorageReference mStorage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mVPagerHeaderAd = (ViewPager) getView().findViewById(R.id.viewpager_main_header_ad);

        MainHeaderAdAdapter adapter = new MainHeaderAdAdapter(getActivity(), DataUtil.getHeaderAdInfo(getActivity(), icons));
        mVPagerHeaderAd.setAdapter(adapter);

        mPublishButton = (Button)getView().findViewById(R.id.home_publish);
        mStorage = FirebaseStorage.getInstance().getReference();
        mPublishButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                Intent publish = new Intent(getActivity(), PublishActivity.class);
                startActivity(publish);

//
            }
        });

    }






}
