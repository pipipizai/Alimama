package com.example.alimama.alimama.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alimama.alimama.R;
import com.example.alimama.alimama.bean.Item;
import com.example.alimama.alimama.fragment.CartFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.List;

import static java.security.AccessController.getContext;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemViewHolder>  {

    private Context mContext;
    private List<Item> itemsList;
    private String item_name;
    private String item_price;
    private String item_image;
    private String item_description;

    public SearchAdapter(Context mContext, List<Item> itemsList){
        this.mContext = mContext;
        this.itemsList = itemsList;
    }



    public void onBindViewHolder(@NonNull SearchAdapter.ItemViewHolder holder, int position) {

        Item item = itemsList.get(position);

        holder.item_nameTextView.setText(item.getName());
        holder.item_priceTextView.setText(item.getPrice());
        Glide.with(mContext).load(item.getImage()).placeholder(R.drawable.default_item_image).into(holder.item_imageImageView);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @NonNull
    @Override
    public SearchAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //for item_row
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list, viewGroup, false);
        //SearchAdapter.ItemViewHolder viewHolder = new SearchAdapter.ItemViewHolder(view);
        return new SearchAdapter.ItemViewHolder(view);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_imageImageView;
        private TextView item_nameTextView;
        private TextView item_priceTextView;
        private TextView item_descriptiontTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_imageImageView = (ImageView)itemView.findViewById(R.id.search_item_image);
            item_nameTextView = (TextView)itemView.findViewById(R.id.search_item_name);
            item_priceTextView = (TextView)itemView.findViewById(R.id.search_item_price);
            item_descriptiontTextView = (TextView)itemView.findViewById(R.id.search_item_description);

        }

    }
}
