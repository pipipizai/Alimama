package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.alimama.alimama.bean.Item;
import com.example.alimama.alimama.fragment.CartFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FavoriteActivity extends BaseActvity {

    private RecyclerView mCartList;

    private long userID=0;
    private String username;

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
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Item model) {

//                Picasso.get().load(model.getImage()).into(holder.publish_image);
//                holder.setImage(getA,model.getImage);
//                Picasso.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
                Glide.with(FavoriteActivity.this).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.item_image);
                holder.item_name.setText(model.getName());
                holder.item_price.setText(model.getPrice());
                holder.item_description.setText(model.getDescription());
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                //for item_row
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_list, viewGroup, false);
                ItemViewHolder viewHolder = new ItemViewHolder(view);



                return viewHolder;
            }
//
//            public String getKey(int position) {
//                return getSnapshots().getItem(position).getKey();
//            }
//
//            @Override
//            public void onItemLongClick() {
//                onItemLongClick(, );
//            }



//            public void deleteItem(int position){
//                getSnapshots().getSnapshot(position).getRef().removeValue();
//            }

        };



        mCartList.setAdapter(firebaseRecyclerAdapter);

        ItemTouchHelper.SimpleCallback simpleItemCallback =
        new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//                firebaseRecyclerAdapter.deleteItem(viewHolder.getAdapterPosition());
                Toast.makeText(FavoriteActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();

                //final DatabaseReference itemRef = data.getRef(position);
//                getSnapshot(position).getRef().removeValue();
                //firebaseRecyclerAdapter.notifyItemRemoved(position);
                firebaseRecyclerAdapter.getRef(position).removeValue();
                toFavoriteActivity();

            }
        };

        firebaseRecyclerAdapter.startListening();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemCallback);
        itemTouchHelper.attachToRecyclerView(mCartList);

    }



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
