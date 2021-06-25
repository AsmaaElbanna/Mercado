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
import com.iti.mercado.model.HomeAppliance;
import com.iti.mercado.model.ItemPath;
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
import com.iti.mercado.utilities.DatabaseItem;
import com.iti.mercado.utilities.OnRetrieveItem;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private final Context context;
    private List<ItemPath> itemPaths;

    public SearchAdapter(Context context, List<ItemPath> itemPaths) {
        this.context = context;
        this.itemPaths = itemPaths;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_single_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitleTextView.setText(itemPaths.get(position).getItem().getItem_title());
        holder.itemPriceTextView.setText(itemPaths.get(position).getItem().getItem_price() + " EGP");
        Glide.with(context).load(itemPaths.get(position).getItem().getItem_image())
                //.apply(new RequestOptions().override(100,100))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.itemImageView);

        holder.linearLayout.setOnClickListener(v -> {
            subCategorySwitch(itemPaths.get(position), position);
        });
    }

    private void subCategorySwitch(ItemPath itemPath, final int position) {
        if (itemPath.getSubCategory().equals("clothing")) {
            if (itemPath.getCategory().equals("Women's Fashion"))
                DatabaseItem.getItemDetails(itemPath, WomenClothing.class, () -> {
                    openDetails(position);
                });
            else if (itemPath.getCategory().equals("Girl's Fashion") ||
                    itemPath.getCategory().equals("boy's fashion"))
                DatabaseItem.getItemDetails(itemPath, KidsClothing.class, () -> {
                    openDetails(position);
                });
        } else if (itemPath.getSubCategory().equals("shoes"))
            DatabaseItem.getItemDetails(itemPath, KidsShoes.class, () -> {
                openDetails(position);
            });
        else if (itemPath.getSubCategory().equals("bags"))
            DatabaseItem.getItemDetails(itemPath, WomenBags.class, () -> {
                openDetails(position);
            });
        else if (itemPath.getSubCategory().equals("makeUp"))
            DatabaseItem.getItemDetails(itemPath, MakeUp.class, () -> {
                openDetails(position);
            });
        else if (itemPath.getSubCategory().equals("skinCare"))
            DatabaseItem.getItemDetails(itemPath, SkinCare.class, () -> {
                openDetails(position);
            });
        else if (itemPath.getSubCategory().equals("microwaves") ||
                itemPath.getSubCategory().equals("blendersAndMixers"))
            DatabaseItem.getItemDetails(itemPath, HomeAppliance.class, () -> {
                openDetails(position);
            });
        else if (itemPath.getSubCategory().equals("laptopBags"))
            DatabaseItem.getItemDetails(itemPath, LaptopBag.class, () -> {
                openDetails(position);
            });
        else if (itemPath.getSubCategory().equals("laptops"))
            DatabaseItem.getItemDetails(itemPath, Laptop.class, () -> {
                openDetails(position);
            });
        else if (itemPath.getSubCategory().equals("mobiles") ||
                itemPath.getSubCategory().equals("tablets"))
            DatabaseItem.getItemDetails(itemPath, Mobile.class, () -> {
                openDetails(position);
            });
        else if (itemPath.getSubCategory().equals("beautyEquipment") ||
                itemPath.getSubCategory().equals("hairStylers"))
            DatabaseItem.getItemDetails(itemPath, PersonalCare.class, () -> {
                openDetails(position);
            });
    }


    private void openDetails(int itemPathPosition) {
        if (itemPaths.get(itemPathPosition).getItem() instanceof Laptop) {
            Intent intent = new Intent(context, DetailsItemLaptopActivity.class);
            //pass data
            intent.putExtra("MyClass", itemPaths.get(itemPathPosition).getItem());
            context.startActivity(intent);
        } else if (itemPaths.get(itemPathPosition).getItem() instanceof LaptopBag) {
            Intent intent = new Intent(context, DetailsItemLaptopBagActivity.class);
            //pass data
            intent.putExtra("MyClass", itemPaths.get(itemPathPosition).getItem());
            context.startActivity(intent);
        } else if (itemPaths.get(itemPathPosition).getItem() instanceof Mobile) {
            Intent intent = new Intent(context, DetailsItemMobileActivity.class);
            //pass data
            intent.putExtra("MyClass", itemPaths.get(itemPathPosition).getItem());
            context.startActivity(intent);
        } else if (itemPaths.get(itemPathPosition).getItem() instanceof HomeAppliance) {
            Intent intent = new Intent(context, DetailsItemHomeApplianceActivity.class);
            //pass data
            intent.putExtra("MyClass", itemPaths.get(itemPathPosition).getItem());
            context.startActivity(intent);
        } else if (itemPaths.get(itemPathPosition).getItem() instanceof KidsClothing
                || itemPaths.get(itemPathPosition).getItem() instanceof KidsShoes
                || itemPaths.get(itemPathPosition).getItem() instanceof WomenClothing
                || itemPaths.get(itemPathPosition).getItem() instanceof WomenBags
                || itemPaths.get(itemPathPosition).getItem() instanceof MakeUp
                || itemPaths.get(itemPathPosition).getItem() instanceof SkinCare) {
            Intent intent = new Intent(context, DetailsItemFashionActivity.class);
            //pass data
            intent.putExtra("MyClass", itemPaths.get(itemPathPosition).getItem());
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        if (itemPaths != null)
            return itemPaths.size();
        return 0;
    }

    public void setItemPaths(List<ItemPath> itemPaths) {
        this.itemPaths = itemPaths;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitleTextView, itemPriceTextView;
        public ImageView itemImageView;
        public LinearLayout linearLayout;
        public Button addCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.item_container);
            itemTitleTextView = itemView.findViewById(R.id.item_title);
            itemPriceTextView = itemView.findViewById(R.id.item_price);
            itemImageView = itemView.findViewById(R.id.item_image);
            addCart = itemView.findViewById(R.id.add_to_cart);
        }
    }
}
