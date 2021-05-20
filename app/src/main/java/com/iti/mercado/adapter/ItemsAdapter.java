package com.iti.mercado.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.mercado.R;
import com.iti.mercado.activity.DetailsItemClothingActivity;
import com.iti.mercado.model.Item;

import java.util.List;

public class ItemsAdapter<K extends Item> extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {


    private final Context context;
    private final List<K> items;

    public ItemsAdapter(Context context, List<K> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_singel_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitleTextView.setText(items.get(position).getItem_title());
        holder.itemPriceTextView.setText(items.get(position).getItem_price());
        Glide.with(context).load(items.get(position).getItem_image())
                //.apply(new RequestOptions().override(100,100))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.itemImageView);
        holder.linearLayout.setOnClickListener(v -> {

            Intent intent =new Intent(context, DetailsItemClothingActivity.class);
            //pass data
            intent.putExtra("MyClass", items.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitleTextView, itemPriceTextView;
        public ImageView itemImageView;
        public LinearLayout linearLayout ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.item_container);
            itemTitleTextView = itemView.findViewById(R.id.item_title);
            itemPriceTextView = itemView.findViewById(R.id.item_price);
            itemImageView = itemView.findViewById(R.id.item_image);
        }
    }
}
