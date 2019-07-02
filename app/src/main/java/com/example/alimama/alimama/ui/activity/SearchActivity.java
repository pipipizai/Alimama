package com.example.alimama.alimama.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alimama.alimama.R;

public class SearchActivity extends BaseActvity {

    private String searchContent;
    private TextView textViewSeachContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        //display header
        setUpToolbar();
        //display title in header
        setTitle("Search Result");

        //get the transferred data
        Bundle bundle = this.getIntent().getExtras();

        searchContent = bundle.getString("searchContent");

        textViewSeachContent = findViewById(R.id.activity_search_content);
        //show the transferred data
        textViewSeachContent.setText(searchContent);

    }



}


//不必理会这段注释代码，
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });