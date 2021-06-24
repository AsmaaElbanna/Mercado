package com.iti.mercado.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.iti.mercado.model.Cart;
import com.iti.mercado.model.HomeAppliance;
import com.iti.mercado.model.ItemPath;
import com.iti.mercado.model.KidsClothing;
import com.iti.mercado.model.KidsShoes;
import com.iti.mercado.model.Laptop;
import com.iti.mercado.model.LaptopBag;
import com.iti.mercado.model.MakeUp;
import com.iti.mercado.model.Mobile;
import com.iti.mercado.model.SkinCare;
import com.iti.mercado.model.WomenBags;
import com.iti.mercado.model.WomenClothing;
import com.iti.mercado.utilities.DatabaseCart;
import com.iti.mercado.utilities.DatabaseFavorite;
import com.iti.mercado.utilities.ReloadItem;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private final List<ItemPath> itemPaths;
    private final Context context;

    public OfferAdapter(Context context, List<ItemPath> itemPaths) {
        this.context = context;
        this.itemPaths = itemPaths;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (itemPaths.get(position).getItem() != null) {
            holder.itemTitleTextView.setText(itemPaths.get(position).getItem().getItem_title());
            holder.itemPriceTextView.setText(itemPaths.get(position).getItem().getOldPrice()+" EGP");
            holder.itemOldPriceTextView.setText(itemPaths.get(position).getItem().getItem_price()+" EGP");
//            holder.itemOldPriceTextView.setPaintFlags(holder.itemOldPriceTextView.getPaintFlags() |
//                    Paint.STRIKE_THRU_TEXT_FLAG);

            Glide.with(context).load(itemPaths.get(position).getItem().getItem_image())
                    //.apply(new RequestOptions().override(100,100))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.itemImage);

            holder.itemContainerLinearLayout.setOnClickListener(v -> {

                ReloadItem.adapterName = "OfferAdapter";
                ReloadItem.itemNumberInAdapter = position;

                itemPaths.get(position).getItem().setItem_id(itemPaths.get(position).getItemId());

                if (itemPaths.get(position).getItem() instanceof Laptop) {
                    Intent intent = new Intent(context, DetailsItemLaptopActivity.class);
                    //pass data
                    intent.putExtra("MyClass", itemPaths.get(position).getItem());

                    intent.putExtra("category", itemPaths.get(position).getCategory());
                    intent.putExtra("subcategory", itemPaths.get(position).getSubCategory());

                    context.startActivity(intent);
                } else if (itemPaths.get(position).getItem() instanceof LaptopBag) {
                    Intent intent = new Intent(context, DetailsItemLaptopBagActivity.class);
                    //pass data
                    intent.putExtra("MyClass", itemPaths.get(position).getItem());

                    intent.putExtra("category", itemPaths.get(position).getCategory());
                    intent.putExtra("subcategory", itemPaths.get(position).getSubCategory());

                    context.startActivity(intent);
                } else if (itemPaths.get(position).getItem() instanceof Mobile) {
                    Intent intent = new Intent(context, DetailsItemMobileActivity.class);
                    //pass data
                    intent.putExtra("MyClass", itemPaths.get(position).getItem());

                    intent.putExtra("category", itemPaths.get(position).getCategory());
                    intent.putExtra("subcategory", itemPaths.get(position).getSubCategory());

                    context.startActivity(intent);
                } else if (itemPaths.get(position).getItem() instanceof HomeAppliance) {
                    Intent intent = new Intent(context, DetailsItemHomeApplianceActivity.class);
                    //pass data
                    intent.putExtra("MyClass", itemPaths.get(position).getItem());

                    intent.putExtra("category", itemPaths.get(position).getCategory());
                    intent.putExtra("subcategory", itemPaths.get(position).getSubCategory());

                    context.startActivity(intent);
                } else if (itemPaths.get(position).getItem() instanceof KidsClothing
                        || itemPaths.get(position).getItem() instanceof KidsShoes
                        || itemPaths.get(position).getItem() instanceof WomenClothing
                        || itemPaths.get(position).getItem() instanceof WomenBags
                        || itemPaths.get(position).getItem() instanceof MakeUp
                        || itemPaths.get(position).getItem() instanceof SkinCare) {
                    Intent intent = new Intent(context, DetailsItemFashionActivity.class);
                    //pass data
                    intent.putExtra("MyClass", itemPaths.get(position).getItem());

                    intent.putExtra("category", itemPaths.get(position).getCategory());
                    intent.putExtra("subcategory", itemPaths.get(position).getSubCategory());

                    context.startActivity(intent);
                }
            });

            DatabaseFavorite databaseFavorite = new DatabaseFavorite();

            databaseFavorite.read(itemPaths.get(position), flag -> {
                if (flag) {
                    holder.unfavorite.setVisibility(View.GONE);
                    holder.favorite.setVisibility(View.VISIBLE);
                }
            });

            holder.unfavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseFavorite.write(itemPaths.get(position)
                            , () -> {
                                holder.unfavorite.setVisibility(View.GONE);
                                holder.favorite.setVisibility(View.VISIBLE);
                            });
                }
            });

            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseFavorite.delete(itemPaths.get(position), () -> {
                        holder.favorite.setVisibility(View.GONE);
                        holder.unfavorite.setVisibility(View.VISIBLE);
                    });
                }
            });

            // cart part
            Cart cart =new Cart();
            cart.setItemId(itemPaths.get(position).getItemId());
            cart.setCategory(itemPaths.get(position).getCategory());
            cart.setSubCategory(itemPaths.get(position).getSubCategory());
            cart.setCount(1);
            DatabaseCart databaseCart = new DatabaseCart();


            databaseCart.read(cart, flag -> {
                if (flag) {
                    holder.addToCartButton.setText("Added");
                }
            });

            holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.addToCartButton.getText() == "Added") {
                        databaseCart.delete(cart, () -> {
                            holder.addToCartButton.setText("Add to cart");
                        });
                    } else {
                        databaseCart.write(cart
                                , () -> {
                                    holder.addToCartButton.setText("Added");
                                });
                    }
                }
                // twist

            });


        }
    }

    @Override
    public int getItemCount() {
        return itemPaths.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView unfavorite;
        public ImageView favorite;
        public ImageView itemImage;
        public TextView itemPriceTextView;
        public TextView itemOldPriceTextView;
        public TextView itemTitleTextView;
        public Button addToCartButton;
        public LinearLayout itemContainerLinearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unfavorite = itemView.findViewById(R.id.unfavorite);
            favorite = itemView.findViewById(R.id.favorite);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemPriceTextView = itemView.findViewById(R.id.itemPrice);
            itemOldPriceTextView = itemView.findViewById(R.id.itemOldPrice);
            itemTitleTextView = itemView.findViewById(R.id.itemTitle);
            addToCartButton = itemView.findViewById(R.id.addToCart);
            itemContainerLinearLayout = itemView.findViewById(R.id.item_container);
        }
    }
}
