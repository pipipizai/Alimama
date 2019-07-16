package com.example.alimama.alimama.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Typeface;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.Item;
import com.example.alimama.alimama.bean.CategoriesList;
import com.example.alimama.alimama.ui.activity.ItemInformationActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;

public class CategoriesFragment extends Fragment {


    private RecyclerView mCategoriesList;
    private RecyclerView mItemsList;

    private DatabaseReference mCategoriesNameDatabaseRef;
    private DatabaseReference mCategoriesDatabaseRef;
    private DatabaseReference mUserProfileImageDatabaseRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        //字体
        TextView textView1 = getView().findViewById(R.id.categories_head);
        Typeface tf= Typeface.createFromAsset(getContext().getAssets(), "againts.otf");
        textView1.setTypeface(tf);

    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<CategoriesList> options =
                new FirebaseRecyclerOptions.Builder<CategoriesList>()
                        .setQuery(mCategoriesNameDatabaseRef, CategoriesList.class)
                        .build();

        FirebaseRecyclerAdapter<CategoriesList, CategoriesListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CategoriesList, CategoriesListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoriesListViewHolder holder, int position, @NonNull CategoriesList model) {


                holder.categoriesList.setText(model.getCategoriesName());
                final String catcategoriesName = model.getCategoriesName();

                holder.categoriesList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mCategoriesDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Categories").child(catcategoriesName);


                        FirebaseRecyclerOptions<Item> options =
                                new FirebaseRecyclerOptions.Builder<Item>()
                                        .setQuery(mCategoriesDatabaseRef, Item.class)
                                        .build();

                        FirebaseRecyclerAdapter<Item, ItemViewHolder> itemListRecyclerAdapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Item model) {

                                //mUserProfileImageDatabaseRef = FirebaseDatabase.getInstance().getReference().child("User").child(model.getUserName());
                               // String profile_image = String.valueOf(mUserProfileImageDatabaseRef.child("icon"));
                                Glide.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.item_image);

                                Glide.with(getContext()).load(model.getUserProfileImage()).into(holder.user_profile_image);
                                holder.published_user_name.setText(model.getUserName());
                                holder.item_description.setText(model.getDescription());
                                holder.item_price.setText(model.getPrice());
                            }

                            @NonNull
                            @Override
                            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                //for item_row
                                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
                                ItemViewHolder viewHolder = new ItemViewHolder(view);
                                return viewHolder;
                            }
                        };

                        mItemsList.setAdapter(itemListRecyclerAdapter);

                        itemListRecyclerAdapter.startListening();
                    }


                });

            }

            @NonNull
            @Override
            public CategoriesListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                //for categories_list
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categories_list, viewGroup, false);
                CategoriesListViewHolder viewHolder = new CategoriesListViewHolder(view);
                return viewHolder;
            }
        };

        mCategoriesList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();
    }

    public static class CategoriesListViewHolder extends RecyclerView.ViewHolder {

        private TextView categoriesList;


        private CategoriesListViewHolder(View categoriesView) {
            super(categoriesView);

            categoriesList = categoriesView.findViewById(R.id.categories_name);

        }

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout root;
        private ImageView item_image;
        private ImageView user_profile_image;
        private TextView published_user_name;
        private TextView item_price;
        private TextView item_description;



        private ItemViewHolder(View itemView) {
            super(itemView);
//            root = itemView.findViewById(R.id.cat);
            item_image = itemView.findViewById(R.id.categories_item_image);
            published_user_name = itemView.findViewById(R.id.categories_user_name);
            item_price = itemView.findViewById(R.id.categories_item_price);
            item_description = itemView.findViewById(R.id.categories_item_description);
           // item_description = itemView.findViewById(R.id.categories_item_description);
            user_profile_image = itemView.findViewById(R.id.categories_user_profile_image);

        }

    }

    private void initView() {

        mCategoriesList = getView().findViewById(R.id.catrgories_menu_list);
        mCategoriesList.setHasFixedSize(true);
        mCategoriesList.setLayoutManager(new LinearLayoutManager(getContext()));

        mItemsList = getView().findViewById(R.id.catrgories_menu_content);
        mItemsList.setHasFixedSize(true);
        mItemsList.setLayoutManager(new LinearLayoutManager(getContext()));

        mCategoriesNameDatabaseRef = FirebaseDatabase.getInstance().getReference().child("CategoriesName");

    }


}
