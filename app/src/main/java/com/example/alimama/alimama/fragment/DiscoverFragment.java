package com.example.alimama.alimama.fragment;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.Item;
import com.example.alimama.alimama.ui.activity.ItemInformationActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.core.Context;
import com.squareup.picasso.Picasso;

/**
 * Created by LING on 6/5/2019.
 */

public class DiscoverFragment extends Fragment {

    private RecyclerView mItemList;

    private DatabaseReference mDatabaseRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover,container,false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        //字体
        TextView textView1 = getView().findViewById(R.id.discover_head);
        Typeface tf= Typeface.createFromAsset(getContext().getAssets(), "againts.otf");
        textView1.setTypeface(tf);

    }

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
//                Picasso.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
                Glide.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
                holder.publish_name.setText(model.getName());
                holder.publish_price.setText(model.getPrice());
                holder.publish_description.setText(model.getDescription());

                final long itemID=model.getItemID();
                final String itemPublishedUserName=model.getUserName();
                final String itemImage=model.getImage();
                final String itemName=model.getName();
                final String itemPrice=model.getPrice();
                final String itemDescription=model.getDescription();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), ItemInformationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("item id",itemID);
                        bundle.putString("image",itemImage);
                        bundle.putString("name",itemName);
                        bundle.putString("price",itemPrice);
                        bundle.putString("description",itemDescription);
                        bundle.putString("itemPublishedUserName",itemPublishedUserName);
                        intent.putExtras(bundle);

//                        intent.putExtra((String) ItemInformationActivity.ExtraData, itemID);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                //for item_row
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }
        };

        mItemList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout root;
        private ImageView publish_image;
        private TextView publish_name;
        private TextView publish_price;
        private TextView publish_description;


        private ItemViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.item_list);
            publish_image = itemView.findViewById(R.id.item_image);
            publish_name = itemView.findViewById(R.id.item_name);
            publish_price = itemView.findViewById(R.id.item_price);
            publish_description = itemView.findViewById(R.id.item_description);

        }

        private void setImage(Context ctx, String image) {

//            Picasso.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
//            publish_image.setImage
//            Picasso.with(ctx).load(image).placeholder(R.drawable.default_item_image).into(publish_image);

        }

        private void setName(String name) {

            publish_name.setText(name);
        }

        private void setPrice(String price) {

//            String stringPrice= Double.toString(price);
            publish_price.setText(price);
        }

        private void setDescription(String description) {

            publish_description.setText(description);
        }
//

    }


    private void initView() {

        mItemList = getView().findViewById(R.id.item_list);
        mItemList.setHasFixedSize(true);
        mItemList.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Items");

    }

//    /**
//     * ItemView点击事件回调接口
//     */
//    interface OnItemClickListener {
//        void onItemClick(int position);
//    }

}
