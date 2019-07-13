package com.example.alimama.alimama.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Typeface;

import com.example.alimama.alimama.R;

import android.content.ClipData;
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

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class CartFragment extends Fragment {

    private RecyclerView mCartList;

    private long userID=0;
    private String username;

    private DatabaseReference mDatabaseRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart,container,false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        //字体
        TextView textView1 = getView().findViewById(R.id.cart_head);
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

        FirebaseRecyclerAdapter<Item, CartFragment.ItemViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Item, CartFragment.ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartFragment.ItemViewHolder holder, int position, @NonNull Item model) {

//                Picasso.get().load(model.getImage()).into(holder.publish_image);
//                holder.setImage(getA,model.getImage);
//                Picasso.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
                Glide.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.item_image);
                holder.item_name.setText(model.getName());
                holder.item_price.setText(model.getPrice());
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                //for item_row
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list, viewGroup, false);
                CartFragment.ItemViewHolder viewHolder = new CartFragment.ItemViewHolder(view);
                return viewHolder;
            }
        };

        mCartList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout root;
        private ImageView item_image;
        private TextView item_name;
        private TextView item_price;


        private ItemViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.cart_items);
            item_image = itemView.findViewById(R.id.id_iv_image);
            item_name = itemView.findViewById(R.id.id_tv_name);
            item_price = itemView.findViewById(R.id.id_tv_price);

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

        mCartList = getView().findViewById(R.id.cart_items);
        mCartList.setHasFixedSize(true);
        mCartList.setLayoutManager(new LinearLayoutManager(getContext()));

        //1、获取Preferences
        SharedPreferences preferences=getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        userID = preferences.getLong("userid",0);
        username=preferences.getString("username", null);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("cart items");

    }

}
