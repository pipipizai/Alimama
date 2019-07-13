package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.Item;
import com.example.alimama.alimama.fragment.CartFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShoppingHistoryActivity extends BaseActvity {

    private RecyclerView mShoppingHistoryList;

    private long userID=0;
    private String username;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinghistory);

        initView();
        setUpToolbar();
        setTitle("Order Record");
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

        FirebaseRecyclerAdapter<Item, ItemViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Item model) {

//                Picasso.get().load(model.getImage()).into(holder.publish_image);
//                holder.setImage(getA,model.getImage);
//                Picasso.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
                Glide.with(ShoppingHistoryActivity.this).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.item_image);
                holder.item_name.setText(model.getName());
                holder.item_price.setText(model.getPrice());
                holder.item_description.setText(model.getDescription());
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                //for item_row
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shoppinghistory_list, viewGroup, false);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }
        };

        mShoppingHistoryList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout root;
        private ImageView item_image;
        private TextView item_name;
        private TextView item_price;
        private TextView item_description;


        private ItemViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.shopping_history_items);
            item_image = itemView.findViewById(R.id.shopping_history_item_image);
            item_name = itemView.findViewById(R.id.shopping_history_item_name);
            item_price = itemView.findViewById(R.id.shopping_history_item_price);
            item_description = itemView.findViewById(R.id.shopping_history_item_description);

        }

//        private void setImage(Context ctx, String image) {
//
////            Picasso.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
////            publish_image.setImage
////            Picasso.with(ctx).load(image).placeholder(R.drawable.default_item_image).into(publish_image);
//
//        }
//
//        private void setName(String name) {
//
//            item_name.setText(name);
//        }
//
//        private void setPrice(String price) {
//
////            String stringPrice= Double.toString(price);
//            item_price.setText(price);
//        }


    }


    private void initView() {

        mShoppingHistoryList = this.findViewById(R.id.shopping_history_items);
        mShoppingHistoryList.setHasFixedSize(true);
        mShoppingHistoryList.setLayoutManager(new LinearLayoutManager(this));

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        userID = preferences.getLong("userid",0);
        username=preferences.getString("username", null);

        //改一下读取的表"shopping history"
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("favorite items");

    }
}
