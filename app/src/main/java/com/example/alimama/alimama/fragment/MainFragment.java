package com.example.alimama.alimama.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Typeface;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.adapter.MainHeaderAdAdapter;
import com.example.alimama.alimama.adapter.MainMenuAdapter;
import com.example.alimama.alimama.entity.Item;
import com.example.alimama.alimama.ui.activity.ItemInformationActivity;
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
    private ImageButton mMessageButton;
    private Button mSearchButton;

    private EditText mEditTextseachContent;

    //main fragment header icon
    protected int[] icons = {R.mipmap.header_pic_ad1,
            R.mipmap.header_pic_ad2,
            R.mipmap.header_pic_ad1};

    //main fragment menu icon
    protected int[] menuIcons = {R.mipmap.main_menu_food,
            R.mipmap.main_menu_cloth,
            R.mipmap.main_menu_commonly,
            R.mipmap.main_menu_cosmetic,
            R.mipmap.main_menu_digital,
            R.mipmap.main_menu_jewelry,
            R.mipmap.main_menu_drug,
            R.mipmap.main_menu_book};
    protected String[] menus;
    protected ViewPager mVPagerHeaderAd;//主页头部广告
    protected RecyclerView mRecycleViewMenu;//主页主菜单

//    String [] menus;

    private DatabaseReference mDatabaseRef;
    private RecyclerView mItemList; //猜你喜欢
    private StorageReference mStorage;

    protected ImageView mHomeImageView;
    protected ImageView mCategoriesImageView;
    protected ImageView mMessageImageView;
    protected ImageView mMeImageView;


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
        Typeface tf1= Typeface.createFromAsset(getContext().getAssets(), "HandWrite.ttf");
        Typeface tf2= Typeface.createFromAsset(getContext().getAssets(), "PTSans-Regular.ttf");
        textView1.setTypeface(tf1);
        textView2.setTypeface(tf2);

        //菜单
        //布局样式
        /**
         * SET MENU ICON
         */
        mRecycleViewMenu.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(getActivity(), DataUtil.getMainMenu(menuIcons,menus));
        mRecycleViewMenu.setAdapter(mainMenuAdapter);

        /**
         * PUBLISH ITEMS FUNCTION
         */
//        mPublishButton = getView().findViewById(R.id.home_publish);
        mStorage = FirebaseStorage.getInstance().getReference();
//        mPublishButton.setOnClickListener(new View.OnClickListener( ) {
//            @Override
//            public void onClick(View view) {
//
//                Intent publish = new Intent(getActivity(), PublishActivity.class);
//                startActivity(publish);
//
//            }
//        });

        /**
         * MESSAGE FUNCTION
         */

        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoriesImageView.getDrawable().setLevel(1);
                mMessageImageView.getDrawable().setLevel(2);
                mMeImageView.getDrawable().setLevel(1);
                mHomeImageView.getDrawable().setLevel(1);
                mMessageImageView.setVisibility(View.VISIBLE);

                    getFragmentManager()
                          .beginTransaction()//将当前fragment加入到返回栈中
                          .replace(R.id.container_content, new MessageFragment())
                            .addToBackStack(null)
                            .commit();
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
            protected void onBindViewHolder(@NonNull final ItemViewHolder holder, int position, @NonNull final Item model) {

//                Picasso.get().load(model.getImage()).into(holder.publish_image);
//                holder.setImage(getA,model.getImage);
//                Picasso.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
                Glide.with(getContext()).load(model.getImage()).placeholder(R.drawable.default_item_image).into(holder.publish_image);
                holder.publish_name.setText(model.getName());
                holder.publish_price.setText(model.getPrice());
                holder.publish_description.setText(model.getDescription());

                holder.root.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                if (v.getId() == R.id.layout_item_row) {
                                    holder.root.setScaleX((float) 0.95);
                                    holder.root.setScaleY((float) 0.95);
                                }

                                Intent intent = new Intent(getActivity(), ItemInformationActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putLong("item id",model.getItemID());
                                bundle.putString("image",model.getImage());
                                bundle.putString("name",model.getName());
                                bundle.putString("price",model.getPrice());
                                bundle.putString("description",model.getDescription());
                                bundle.putString("itemPublishedUserName",model.getUserName());
                                bundle.putString("userProfileImage",model.getUserProfileImage());
                                intent.putExtras(bundle);

                                startActivity(intent);

                                break;
                            }

                            case MotionEvent.ACTION_UP: {
                                if (v.getId() == R.id.layout_item_row) {
                                    holder.root.setScaleX(1);
                                    holder.root.setScaleY(1);
                                }


                                break;
                            }

                        }

                        return false;
                    }
                });
            }
    //    };
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent intent = new Intent(getActivity(), ItemInformationActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putLong("item id",model.getItemID());
//                        bundle.putString("image",model.getImage());
//                        bundle.putString("name",model.getName());
//                        bundle.putString("price",model.getPrice());
//                        bundle.putString("description",model.getDescription());
//                        bundle.putString("itemPublishedUserName",model.getUserName());
//                        bundle.putString("userProfileImage",model.getUserProfileImage());
//                        intent.putExtras(bundle);
//
////                        intent.putExtra((String) ItemInformationActivity.ExtraData, itemID);
//                        startActivity(intent);
//                    }


          //  }

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

//    @Override
//    public void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//        mItemList.setLayoutManager(new GridLayoutManager(getContext(),2));
//
//    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private CardView root;
        private ImageView publish_image;
        private TextView publish_name;
        private TextView publish_price;
        private TextView publish_description;


        private ItemViewHolder(View itemView) {
            super(itemView);
          //  root = itemView.findViewById(R.id.layout_item_row);
            root = itemView.findViewById(R.id.layout_item_row);
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
        menus = this.getActivity().getResources().getStringArray(R.array.main_menu);

        mItemList = getView().findViewById(R.id.item_list);
        mItemList.setHasFixedSize(true);
        mItemList.setLayoutManager(new GridLayoutManager(getContext(),2));

        mHomeImageView = getActivity().findViewById(R.id.menu_icon_home);
        mCategoriesImageView = getActivity().findViewById(R.id.menu_icon_categories);
        mMessageImageView = getActivity().findViewById(R.id.menu_icon_message);
        mMeImageView = getActivity().findViewById(R.id.menu_icon_me);

        //initialize searchButton & EditText(search content is in it)
        //R.id.button_search button_search is id of mSearchButton in fragment_main layout
        mSearchButton = getView().findViewById(R.id.button_search);
        mMessageButton = getView().findViewById(R.id.main_header__message);
        mEditTextseachContent = getView().findViewById(R.id.main_search_content);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Items");

    }

    private void font(){


    }

}
