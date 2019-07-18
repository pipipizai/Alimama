package com.example.alimama.alimama.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class MyPublishActivity extends BaseActvity {

    private RecyclerView mPublishedItemList;
    private DeleteDialog deleteDialog;
    private long userID=0;
    private String username;
    private String userProfileImage;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_published);

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

                Glide.with(MyPublishActivity.this).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.item_image);
                holder.item_name.setText(model.getName());
                holder.item_price.setText(model.getPrice());
                holder.item_description.setText(model.getDescription());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MyPublishActivity.this, ItemInformationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("item id",model.getItemID());
                        bundle.putString("image", model.getImage());
                        bundle.putString("name",model.getName());
                        bundle.putString("price",model.getPrice());
                        bundle.putString("description",model.getDescription());
                        bundle.putString("itemPublishedUserName",model.getUserName());
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
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publish_list, viewGroup, false);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }
        };

        mPublishedItemList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();

        ItemTouchHelper.SimpleCallback simpleItemCallback =
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                mPublishedItemList.getLayoutParams());

                        deleteDialog = new DeleteDialog(MyPublishActivity.this,params);

                        deleteDialog.setTitle("Alert");
                        deleteDialog.setMessage("Are you sure to delete");
                        deleteDialog.setYesOnclickListener("YES", new DeleteDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {

                                Toast.makeText(MyPublishActivity.this, "Deleted ", Toast.LENGTH_SHORT).show();
                                //Remove swiped item from list and notify the RecyclerView
                                final int position = viewHolder.getAdapterPosition();

                                firebaseRecyclerAdapter.getRef(position).removeValue();

                                deleteDialog.dismiss();

                                finish();
                                Intent intent = new Intent(MyPublishActivity.this,MyPublishActivity.class);
                                startActivity(intent);

                                //    onCreate(null);
                            }
                        });
                        deleteDialog.setNoOnclickListener("NO", new DeleteDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {

                                deleteDialog.dismiss();
                                finish();
                                Intent intent = new Intent(MyPublishActivity.this,MyPublishActivity.class);
                                startActivity(intent);
                                // onCreate(null);
                            }
                        });

                        deleteDialog.show();

                    }
                };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemCallback);
        itemTouchHelper.attachToRecyclerView(mPublishedItemList);


    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout root;
        private ImageView item_image;
        private TextView item_name;
        private TextView item_price;
        private TextView item_description;


        private ItemViewHolder(View itemView) {
            super(itemView);
//            root = itemView.findViewById(R.id.published_items);
            item_image = itemView.findViewById(R.id.published_item_image);
            item_name = itemView.findViewById(R.id.published_item_name);
            item_price = itemView.findViewById(R.id.published_item_price);
            item_description = itemView.findViewById(R.id.published_item_description);

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

        mPublishedItemList = this.findViewById(R.id.publish_items);
        mPublishedItemList.setHasFixedSize(true);
        mPublishedItemList.setLayoutManager(new LinearLayoutManager(this));

        //1、获取Preferences
        SharedPreferences preferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        userID = preferences.getLong("userid",0);
        username=preferences.getString("username", null);
        userProfileImage = preferences.getString("userIcon", null);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(String.valueOf(username)).child("published items");

        setTitle("My Published");
    }
}
