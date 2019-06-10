package com.example.alimama.alimama.fragment;

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

/**
 * Created by LING on 6/5/2019.
 */

public class DiscoverFragment extends Fragment {

    private RecyclerView mItemList;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover,container,false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter.startListening();

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout root;
        public TextView publish_name;
        public TextView publish_price;
        public TextView publish_description;

        public ItemViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.item_list);
            publish_name = itemView.findViewById(R.id.item_name);
            publish_price = itemView.findViewById(R.id.item_price);
            publish_description = itemView.findViewById(R.id.item_description);

//
        }

        public void setItemName(String name) {

            publish_name.setText(name);
        }

        public void setItemPrice(String price) {

//            String stringPrice= Double.toString(price);
            publish_price.setText(price);
        }

        public void setItemDescription(String description) {

            publish_description.setText(description);
        }
//

    }


    private void initView() {

        linearLayoutManager = new LinearLayoutManager(getContext());
        mItemList = (RecyclerView) getView().findViewById(R.id.item_list);
        mItemList.setHasFixedSize(true);
        mItemList.setLayoutManager(linearLayoutManager);
        fetch();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item").child("LguTz87fGRK-gJBPase");

    }

    //

    private void fetch() {
        Query query = (Query) FirebaseDatabase.getInstance()
                .getReference()
                .child("Item");

        FirebaseRecyclerOptions<Item> options =
                new FirebaseRecyclerOptions.Builder<Item>()
                        .setQuery(query, new SnapshotParser<Item>() {
                            @NonNull
                            @Override
                            public Item parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Item(snapshot.child("Item Image").getValue().toString(),
                                        snapshot.child("Item Name").getValue().toString(),
                                        snapshot.child("Item Price").getValue().toString(),
                                        snapshot.child("Item Decription").getValue().toString());
                            }
                        })
                        .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Item model) {

//                View view = LayoutInflater.from(getParentFragment().getContext())
//                        .inflate(R.layout.item_row, getParentFragment().getContext(),false);

                holder.setItemName(model.getName());
                holder.setItemPrice(model.getPrice());
                holder.setItemDescription(model.getDescription());

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_row, viewGroup, false);

                ItemViewHolder viewHolder = new ItemViewHolder(view);


                return viewHolder;

            }
        };


        mItemList.setAdapter(firebaseRecyclerAdapter);

    }


}
