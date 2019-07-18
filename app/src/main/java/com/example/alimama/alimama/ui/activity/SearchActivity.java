package com.example.alimama.alimama.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.graphics.Typeface;

import com.example.alimama.alimama.R;
import com.example.alimama.alimama.adapter.SearchAdapter;
import com.example.alimama.alimama.entity.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActvity {

    private String searchContent;
    private TextView textViewSeachContent;
    private RecyclerView mSearchListRecycleView;
    private List<Item> itemList;

    private DatabaseReference itemsDatabaseRef;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSearchRequireInfo();

        initView();

        //get the transferred data
        Bundle bundle = this.getIntent().getExtras();

        searchContent = bundle.getString("searchContent");

        textViewSeachContent = findViewById(R.id.activity_search_content);
        //show the transferred data
        textViewSeachContent.setText(searchContent);

        readItems(searchContent);

        //select * from Items
//        itemsDatabaseRef.addListenerForSingleValueEvent(valueEventListener);


    }

    private void readItems(final String searchContent){

        Query query = itemsDatabaseRef;

        itemList =new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            // itemsDatabaseRef.child(searchContent).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                itemList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Item item = snapshot.getValue(Item.class);
                        if(item.getName().equals(searchContent))
                            {itemList.add(item);}
                    }
                    adapter = new SearchAdapter(SearchActivity.this,itemList);
                    mSearchListRecycleView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void getSearchRequireInfo() {
        //get the transferred data
        Bundle bundle = this.getIntent().getExtras();

        searchContent = bundle.getString("searchContent");


    }

    private void initView() {
        //display header
        setUpToolbar();
        //display title in header
        setTitle("Search Result");

        mSearchListRecycleView = findViewById(R.id.search_items);

//        mMessageRecyclerView = findViewById(R.id.message);
        mSearchListRecycleView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        mSearchListRecycleView.setLayoutManager(linearLayoutManager);

        textViewSeachContent = findViewById(R.id.activity_search_content);

     //   TextView textView = findViewById(R.id.activity_search_content);
        Typeface tf= Typeface.createFromAsset(getAssets(), "PTSans-Regular.ttf");
        textViewSeachContent.setTypeface(tf);
        //show the transferred data
        textViewSeachContent.setText("Search result"+searchContent);

        itemsDatabaseRef = FirebaseDatabase.getInstance().getReference("Items");
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