package com.example.alimama.alimama.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Typeface;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.adapter.MainHeaderAdAdapter;
import com.example.alimama.alimama.adapter.MainMenuAdapter;
import com.example.alimama.alimama.bean.Item;
import com.example.alimama.alimama.ui.activity.ItemInformationActivity;
import com.example.alimama.alimama.ui.activity.PublishActivity;
import com.example.alimama.alimama.ui.activity.SearchActivity;
import com.example.alimama.alimama.util.DataUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by LING on 6/5/2019.
 * 主界面视图 - HOME
 */

public class MainFragment extends Fragment {

    public static final int GALLERY_INTENT = 2;
    private Button mPublishButton;
    private Button mSearchButton;

    private EditText mEditTextseachContent;

    //main fragment header icon
    protected int[] icons = {R.mipmap.header_pic_ad1,
            R.mipmap.header_pic_ad2,
            R.mipmap.header_pic_ad1};

    //main fragment menu icon
    protected int[] menuIcons = {R.mipmap.main_menu_book,
            R.mipmap.main_menu_home,
            R.mipmap.main_menu_food,
            R.mipmap.main_menu_electronic_product};
    protected ViewPager mVPagerHeaderAd;//主页头部广告
    protected RecyclerView mRecycleViewMenu;//主页主菜单

//    String [] menus;

    private DatabaseReference mDatabaseRef;
    private RecyclerView mItemList; //猜你喜欢
    private StorageReference mStorage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        initView();
        
        MainHeaderAdAdapter adapter = new MainHeaderAdAdapter(getActivity(), DataUtil.getHeaderAdInfo(getActivity(), icons));
        mVPagerHeaderAd.setAdapter(adapter);


        //字体
        TextView textView1 = getView().findViewById(R.id.txt_guess_you_like);
        TextView textView2= getView().findViewById(R.id.main_search_content);
        Typeface tf1= Typeface.createFromAsset(getContext().getAssets(), "againts.otf");
        Typeface tf2= Typeface.createFromAsset(getContext().getAssets(), "HandWrite.ttf");
        textView1.setTypeface(tf2);
        textView2.setTypeface(tf1);

        //菜单
        //布局样式
        /**
         * SET MENU ICON
         */
        mRecycleViewMenu.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(getActivity(), DataUtil.getMainMenu(menuIcons));
        mRecycleViewMenu.setAdapter(mainMenuAdapter);

        /**
         * PUBLISH ITEMS FUNCTION
         */
        mPublishButton = getView().findViewById(R.id.home_publish);
        mStorage = FirebaseStorage.getInstance().getReference();
        mPublishButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                Intent publish = new Intent(getActivity(), PublishActivity.class);
                startActivity(publish);

            }
        });

        /**
         * SEARCH FUNCTION
         */
        //set listener for search button
        //if user click search button, the function will be called
        mSearchButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                //get the content of EditTextView(search content is in it)
                final String searchContent = mEditTextseachContent.getText().toString();
                
                
                Intent search = new Intent(getActivity(), SearchActivity.class);
               
                //the data need to transfer to search activity
                Bundle bundle = new Bundle();
                bundle.putString("searchContent",searchContent);
                search.putExtras(bundle);

                startActivity(search);

            }
        });

       

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
//                holder.setImage(getA,model.getImage);
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


    /**
     * initialize the view
     */
    private void initView() {

        mVPagerHeaderAd = (ViewPager) getView().findViewById(R.id.viewpager_main_header_ad);
        mRecycleViewMenu = (RecyclerView) getView().findViewById(R.id.recycleview_main_menu);

        mItemList = getView().findViewById(R.id.item_list);
        mItemList.setHasFixedSize(true);
        mItemList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        //initialize searchButton & EditText(search content is in it)
        //R.id.button_search button_search is id of mSearchButton in fragment_main layout
        mSearchButton = getView().findViewById(R.id.button_search);
        mEditTextseachContent = getView().findViewById(R.id.main_search_content);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Items");

    }

    private void font(){


    }

}
