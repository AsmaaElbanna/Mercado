package com.iti.mercado.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.iti.mercado.activity.DetailsItemPersonalCareActivity;
import com.iti.mercado.model.Cart;
import com.iti.mercado.model.ItemPath;
import com.iti.mercado.model.HomeAppliance;
import com.iti.mercado.model.Item;
import com.iti.mercado.model.KidsClothing;
import com.iti.mercado.model.KidsShoes;
import com.iti.mercado.model.Laptop;
import com.iti.mercado.model.LaptopBag;
import com.iti.mercado.model.MakeUp;
import com.iti.mercado.model.Mobile;
import com.iti.mercado.model.PersonalCare;
import com.iti.mercado.model.SkinCare;
import com.iti.mercado.model.WomenBags;
import com.iti.mercado.model.WomenClothing;
import com.iti.mercado.utilities.DatabaseCart;
import com.iti.mercado.utilities.DatabaseFavorite;

import java.util.List;

public class ItemsAdapter<K extends Item> extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {


    private final Context context;
    private final List<K> items;
    private final String category, sub_category;

    public ItemsAdapter(Context context, List<K> items, String category, String sub_category) {
        this.context = context;
        this.items = items;
        this.category = category;
        this.sub_category = sub_category;
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

            if (items.get(position) instanceof Laptop) {
                Intent intent = new Intent(context, DetailsItemLaptopActivity.class);
                //pass data
                intent.putExtra("MyClass", items.get(position));
                context.startActivity(intent);
            } else if (items.get(position) instanceof LaptopBag) {
                Intent intent = new Intent(context, DetailsItemLaptopBagActivity.class);
                //pass data
                intent.putExtra("MyClass", items.get(position));
                context.startActivity(intent);
            } else if (items.get(position) instanceof Mobile) {
                Intent intent = new Intent(context, DetailsItemMobileActivity.class);
                //pass data
                intent.putExtra("MyClass", items.get(position));
                context.startActivity(intent);
            } else if (items.get(position) instanceof HomeAppliance) {
                Intent intent = new Intent(context, DetailsItemHomeApplianceActivity.class);
                //pass data
                intent.putExtra("MyClass", items.get(position));
                context.startActivity(intent);
            } else if (items.get(position) instanceof PersonalCare) {
                Intent intent = new Intent(context, DetailsItemPersonalCareActivity.class);
                //pass data
                intent.putExtra("MyClass", items.get(position));
                context.startActivity(intent);
            } else if (items.get(position) instanceof KidsClothing || items.get(position) instanceof KidsShoes || items.get(position) instanceof WomenClothing || items.get(position) instanceof WomenBags || items.get(position) instanceof MakeUp || items.get(position) instanceof SkinCare) {
                Intent intent = new Intent(context, DetailsItemFashionActivity.class);
                //pass data
                intent.putExtra("MyClass", items.get(position));
                context.startActivity(intent);
            }
        });

        //Favorite part

        ItemPath favoriteItem = new ItemPath();
        favoriteItem.setItemId(items.get(position).getItem_id());
        favoriteItem.setCategory(category);
        favoriteItem.setSubCategory(sub_category);
        DatabaseFavorite databaseFavorite = new DatabaseFavorite();

        databaseFavorite.read(favoriteItem, flag -> {
            if (flag) {
                holder.unFavoriteImage.setVisibility(View.GONE);
                holder.favoriteImage.setVisibility(View.VISIBLE);
            }
        });

        Log.i("TAG", "onBindViewHolder: " + favoriteItem.getItemId());

        holder.unFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseFavorite.write(favoriteItem
                        , () -> {
                            holder.unFavoriteImage.setVisibility(View.GONE);
                            holder.favoriteImage.setVisibility(View.VISIBLE);
                        });
            }
        });

        holder.favoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseFavorite.delete(favoriteItem, () -> {
                    holder.favoriteImage.setVisibility(View.GONE);
                    holder.unFavoriteImage.setVisibility(View.VISIBLE);
                });
            }
        });

        // cart part

        Cart cart =new Cart();
        cart.setItemId(items.get(position).getItem_id());
        cart.setCategory(category);
        cart.setSubCategory(sub_category);
        cart.setCount(1);
        DatabaseCart databaseCart = new DatabaseCart();

        databaseCart.read(cart, flag -> {
            if (flag) {
                holder.addCart.setText("Added");
            }
        });
        holder.addCart.setOnClickListener(v -> {
            databaseCart.write(cart
                    , () -> {
                      holder.addCart.setText("Added");
                    });
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitleTextView, itemPriceTextView;
        public ImageView itemImageView, favoriteImage, unFavoriteImage;
        public LinearLayout linearLayout;
        Button addCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.item_container);
            itemTitleTextView = itemView.findViewById(R.id.item_title);
            itemPriceTextView = itemView.findViewById(R.id.item_price);
            itemImageView = itemView.findViewById(R.id.item_image);
            favoriteImage = itemView.findViewById(R.id.favorite);
            unFavoriteImage = itemView.findViewById(R.id.unfavorite);
            addCart = itemView.findViewById(R.id.add_to_cart);
        }
    }
}
