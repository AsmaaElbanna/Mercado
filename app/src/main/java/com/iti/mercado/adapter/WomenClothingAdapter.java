package com.iti.mercado.adapter;

import android.content.Context;
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
import com.iti.mercado.model.WomenClothing;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WomenClothingAdapter extends RecyclerView.Adapter<WomenClothingAdapter.ViewHolder>{
    private final Context context;
    private List<WomenClothing> models;

    public WomenClothingAdapter(Context context, List<WomenClothing> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_singel_product, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.itemTitleTextView.setText(models.get(position).getItem_title());
        holder.itemPriceTextView.setText(models.get(position).getItem_price());
        Glide.with(context).load(models.get(position).getItem_image())
                //.apply(new RequestOptions().override(100,100))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.itemImageView);
        holder.linearLayout.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitleTextView, itemPriceTextView;
        public ImageView itemImageView;
        public LinearLayout linearLayout ;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            linearLayout = layout.findViewById(R.id.item_container);
            itemTitleTextView = layout.findViewById(R.id.item_title);
            itemPriceTextView = layout.findViewById(R.id.item_price);
            itemImageView = layout.findViewById(R.id.item_image);
        }
    }
}
