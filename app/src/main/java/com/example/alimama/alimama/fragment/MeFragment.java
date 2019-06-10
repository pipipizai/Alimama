package com.example.alimama.alimama.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alimama.alimama.ui.activity.LoginActivity;
import com.example.alimama.alimama.R;

/**
 * Created by LING on 6/5/2019.
 */

public class MeFragment extends Fragment {

    protected Button mBtnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtnLogin = (Button)getView().findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录
                Intent login = new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
            }
        });


    }
}
