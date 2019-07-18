package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.entity.Item;
import com.example.alimama.alimama.util.DeleteDialog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FavoriteActivity extends BaseActvity {

    private RecyclerView mCartList;
    private DeleteDialog deleteDialog;

    private long userID=0;
    private String username;
    private boolean deleteConfirm=false;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        initView();
        setUpToolbar();
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Item> options =
                new FirebaseRecyclerOptions.Builder<Item>()
                        .setQuery(mDatabaseRef, Item.class)
                        .build();

        final FirebaseRecyclerAdapter<Item, ItemViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Item model) {

                Glide.with(FavoriteActivity.this).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.item_image);
                holder.item_name.setText(model.getName());
                holder.item_price.setText(model.getPrice());
                holder.item_description.setText(model.getDescription());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(FavoriteActivity.this, ItemInformationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("item id",model.getItemID());
                        bundle.putString("image", model.getImage());
                        bundle.putString("name",model.getName());
                        bundle.putString("price",model.getPrice());
                        bundle.putString("description",model.getDescription());
                        bundle.putString("itemPublishedUserName",model.getDescription());
                        bundle.putString("userProfileImage",model.getUserProfileImage());
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                //for item_row
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_list, viewGroup, false);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }
        };

        mCartList.setAdapter(firebaseRecyclerAdapter);

        ItemTouchHelper.SimpleCallback simpleItemCallback =
        new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                // public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

//                confirmDelete();
//                if(deleteConfirm){
//                    Toast.makeText(FavoriteActivity.this, "Deleted ", Toast.LENGTH_SHORT).show();
//                    //Remove swiped item from list and notify the RecyclerView
//                    final int position = viewHolder.getAdapterPosition();
//
//                    firebaseRecyclerAdapter.getRef(position).removeValue();
//                    toFavoriteActivity();
//                }else{
//                    toFavoriteActivity();
//                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        mCartList.getLayoutParams());

                deleteDialog = new DeleteDialog(FavoriteActivity.this,params);

                deleteDialog.setTitle("Alert");
                deleteDialog.setMessage("Are you sure to delete");
                deleteDialog.setYesOnclickListener("YES", new DeleteDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {

                    Toast.makeText(FavoriteActivity.this, "Deleted ", Toast.LENGTH_SHORT).show();
                    //Remove swiped item from list and notify the RecyclerView
                    final int position = viewHolder.getAdapterPosition();

                    firebaseRecyclerAdapter.getRef(position).removeValue();

                    deleteDialog.dismiss();

                    finish();
                    Intent intent = new Intent(FavoriteActivity.this,FavoriteActivity.class);
                    startActivity(intent);

                    //    onCreate(null);
                    }
                });
                deleteDialog.setNoOnclickListener("NO", new DeleteDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        deleteDialog.dismiss();
                        finish();
                        Intent intent = new Intent(FavoriteActivity.this,FavoriteActivity.class);
                        startActivity(intent);
                       // onCreate(null);
                    }
                });

                deleteDialog.show();

            }
        };

        firebaseRecyclerAdapter.startListening();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemCallback);
        itemTouchHelper.attachToRecyclerView(mCartList);

    }

//    private void confirmDelete() {
//
//
//        deleteDialog = new DeleteDialog(FavoriteActivity.this);
//
//        deleteDialog.setTitle("Alert");
//        deleteDialog.setMessage("Are you sure to delete");
//        deleteDialog.setYesOnclickListener("YES", new DeleteDialog.onYesOnclickListener() {
//            @Override
//            public void onYesClick() {
//                deleteConfirm=true;
//            }
//        });
//        deleteDialog.setNoOnclickListener("NO", new DeleteDialog.onNoOnclickListener() {
//            @Override
//            public void onNoClick() {
//                deleteConfirm=false;
//            }
//        });
//
//        deleteDialog.show();
//
//
//    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout root;
        private ImageView item_image;
        private TextView item_name;
        private TextView item_price;
        private TextView item_description;

        private ItemViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.favorite_items);
            item_image = itemView.findViewById(R.id.favorite_item_image);
            item_name = itemView.findViewById(R.id.favorite_item_name);
            item_price = itemView.findViewById(R.id.favorite_item_price);
            item_description = itemView.findViewById(R.id.favorite_item_description);

        }
    }


    private void initView() {

        mCartList = this.findViewById(R.id.favorite_items);
        mCartList.setHasFixedSize(true);
        mCartList.setLayoutManager(new LinearLayoutManager(this));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                mCartList.getLayoutParams());


        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        userID = preferences.getLong("userid",0);
        username=preferences.getString("username", null);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("favorite items");

        setTitle("My Favorite");
    }

    private void toFavoriteActivity() {
        Intent intent = new Intent(this,FavoriteActivity.class);
        startActivity(intent);
    }
}
