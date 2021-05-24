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
import com.iti.mercado.activity.DetailsItemFashionActivity;
import com.iti.mercado.activity.DetailsItemHomeApplianceActivity;
import com.iti.mercado.activity.DetailsItemLaptopActivity;
import com.iti.mercado.activity.DetailsItemLaptopBagActivity;
import com.iti.mercado.activity.DetailsItemMobileActivity;
import com.iti.mercado.model.FavoriteItem;
import com.iti.mercado.model.HomeAppliance;
import com.iti.mercado.model.KidsClothing;
import com.iti.mercado.model.KidsShoes;
import com.iti.mercado.model.Laptop;
import com.iti.mercado.model.LaptopBag;
import com.iti.mercado.model.MakeUp;
import com.iti.mercado.model.Mobile;
import com.iti.mercado.model.SkinCare;
import com.iti.mercado.model.WomenBags;
import com.iti.mercado.model.WomenClothing;
import com.iti.mercado.utilities.DatabaseFavorite;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final Context context;
    private final List<FavoriteItem> favoriteItems;

    public FavoriteAdapter(Context context, List<FavoriteItem> favoriteItems) {
        this.context = context;
        this.favoriteItems = favoriteItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_singel_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (favoriteItems.get(position).getItem() != null) {
            holder.itemTitleTextView.setText(favoriteItems.get(position).getItem().getItem_title());
            holder.itemPriceTextView.setText(favoriteItems.get(position).getItem().getItem_price());
            Glide.with(context).load(favoriteItems.get(position).getItem().getItem_image())
                    //.apply(new RequestOptions().override(100,100))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.itemImageView);
            holder.linearLayout.setOnClickListener(v -> {

                if (favoriteItems.get(position).getItem() instanceof Laptop) {
                    Intent intent = new Intent(context, DetailsItemLaptopActivity.class);
                    //pass data
                    intent.putExtra("MyClass", favoriteItems.get(position).getItem());
                    context.startActivity(intent);
                } else if (favoriteItems.get(position).getItem() instanceof LaptopBag) {
                    Intent intent = new Intent(context, DetailsItemLaptopBagActivity.class);
                    //pass data
                    intent.putExtra("MyClass", favoriteItems.get(position).getItem());
                    context.startActivity(intent);
                } else if (favoriteItems.get(position).getItem() instanceof Mobile) {
                    Intent intent = new Intent(context, DetailsItemMobileActivity.class);
                    //pass data
                    intent.putExtra("MyClass", favoriteItems.get(position).getItem());
                    context.startActivity(intent);
                } else if (favoriteItems.get(position).getItem() instanceof HomeAppliance) {
                    Intent intent = new Intent(context, DetailsItemHomeApplianceActivity.class);
                    //pass data
                    intent.putExtra("MyClass", favoriteItems.get(position).getItem());
                    context.startActivity(intent);
                } else if (favoriteItems.get(position).getItem() instanceof KidsClothing
                        || favoriteItems.get(position).getItem() instanceof KidsShoes
                        || favoriteItems.get(position).getItem() instanceof WomenClothing
                        || favoriteItems.get(position).getItem() instanceof WomenBags
                        || favoriteItems.get(position).getItem() instanceof MakeUp
                        || favoriteItems.get(position).getItem() instanceof SkinCare) {
                    Intent intent = new Intent(context, DetailsItemFashionActivity.class);
                    //pass data
                    intent.putExtra("MyClass", favoriteItems.get(position).getItem());
                    context.startActivity(intent);
                }
            });

            //Favorite part
            FavoriteItem favoriteItem = favoriteItems.get(position);
            DatabaseFavorite databaseFavorite = new DatabaseFavorite();

            holder.unFavoriteImage.setVisibility(View.GONE);
            holder.favoriteImage.setVisibility(View.VISIBLE);

            holder.favoriteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseFavorite.delete(favoriteItem, () -> {
                        favoriteItems.remove(position);
                        notifyDataSetChanged();
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return favoriteItems.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitleTextView, itemPriceTextView;
        public ImageView itemImageView, favoriteImage, unFavoriteImage;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.item_container);
            itemTitleTextView = itemView.findViewById(R.id.item_title);
            itemPriceTextView = itemView.findViewById(R.id.item_price);
            itemImageView = itemView.findViewById(R.id.item_image);
            favoriteImage = itemView.findViewById(R.id.favorite);
            unFavoriteImage = itemView.findViewById(R.id.unfavorite);
        }
    }
}
