package com.iti.mercado.adapter;

import android.content.Context;
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
import com.iti.mercado.model.Cart;
import com.iti.mercado.utilities.CountSubPrice;
import com.iti.mercado.utilities.DatabaseCart;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final List<Cart> carts;
    private CountSubPrice countSubPrice;
    double subTotal = 0;

    //private int count=1;
    public CartAdapter(Context context, List<Cart> carts, CountSubPrice countSubPrice) {

        this.context = context;
        this.carts = carts;
        this.countSubPrice = countSubPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_single_cart, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (carts.get(position).getItem() == null) {

        } else if (carts.get(position).getItem() != null) {

            holder.titleTextView.setText(carts.get(position).getItem().getItem_title());
            holder.priceTextView.setText(carts.get(position).getItem().getItem_price());
            holder.amountTextView.setText(carts.get(position).getCount() + "");
            // count=Integer.parseInt((String) holder.amountTextView.getText());
            Glide.with(context).load(carts.get(position).getItem().getItem_image())
                    //.apply(new RequestOptions().override(100,100))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.itemImageView);
            if(position==0 && subTotal!=0){

            }else {
                Double price = Double.valueOf(carts.get(position).getItem().getItem_price());
                subTotal = price * carts.get(position).getCount();
                countSubPrice.countSubTotal(subTotal);
            }


//            holder.linearLayout.setOnClickListener(v -> {
//                if (carts.get(position).getItem() instanceof Laptop) {
//                    Intent intent = new Intent(context, DetailsItemLaptopActivity.class);
//                    //pass data
//                    intent.putExtra("MyClass", carts.get(position).getItem());
//                    context.startActivity(intent);
//                } else if (carts.get(position).getItem() instanceof LaptopBag) {
//                    Intent intent = new Intent(context, DetailsItemLaptopBagActivity.class);
//                    //pass data
//                    intent.putExtra("MyClass", carts.get(position).getItem());
//                    context.startActivity(intent);
//                } else if (carts.get(position).getItem() instanceof Mobile) {
//                    Intent intent = new Intent(context, DetailsItemMobileActivity.class);
//                    //pass data
//                    intent.putExtra("MyClass", carts.get(position).getItem());
//                    context.startActivity(intent);
//                } else if (carts.get(position).getItem() instanceof HomeAppliance) {
//                    Intent intent = new Intent(context, DetailsItemHomeApplianceActivity.class);
//                    //pass data
//                    intent.putExtra("MyClass", carts.get(position).getItem());
//                    context.startActivity(intent);
//                } else if (carts.get(position).getItem() instanceof KidsClothing
//                        || carts.get(position).getItem() instanceof KidsShoes
//                        || carts.get(position).getItem() instanceof WomenClothing
//                        || carts.get(position).getItem() instanceof WomenBags
//                        || carts.get(position).getItem() instanceof MakeUp
//                        || carts.get(position).getItem() instanceof SkinCare) {
//                    Intent intent = new Intent(context, DetailsItemFashionActivity.class);
//                    //pass data
//                    intent.putExtra("MyClass", carts.get(position).getItem());
//                    context.startActivity(intent);
//                }
//            });

            //Favorite part
//            FavoriteItem favoriteItem = favoriteItems.get(position);
//            DatabaseFavorite databaseFavorite = new DatabaseFavorite();
//
//            holder.unFavoriteImage.setVisibility(View.GONE);
//            holder.favoriteImage.setVisibility(View.VISIBLE);
//
//            holder.favoriteImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    databaseFavorite.delete(favoriteItem, () -> {
//                        favoriteItems.remove(position);
//                        notifyDataSetChanged();
//                    });
//                }
//            });

            DatabaseCart databaseCart = new DatabaseCart();
            holder.incAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int countInc = Integer.parseInt((String) holder.amountTextView.getText()) + 1;
                    databaseCart.updateCount(countInc, carts.get(position).getItemId());
                    holder.amountTextView.setText(countInc + "");
                    countSubPrice.countSubTotal(Double.valueOf(carts.get(position).getItem().getItem_price()));
                }
            });
            holder.decAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int countDec = Integer.parseInt((String) holder.amountTextView.getText()) - 1;
                    if (countDec >= 1) {
                        carts.get(position).setCount(countDec);
//                        countDec = 1;
//                        databaseCart.updateCount(countDec, carts.get(position).getItemId());
//                        holder.amountTextView.setText(countDec + "");
//                      //  countSubPrice.countSubTotal(-Double.valueOf(carts.get(position).getItem().getItem_price()));
//                    } else {
                        databaseCart.updateCount(countDec, carts.get(position).getItemId());
                        holder.amountTextView.setText(countDec + "");
                        countSubPrice.countSubTotal(-Double.valueOf(carts.get(position).getItem().getItem_price()));
                    }
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, priceTextView, amountTextView;
        ImageView itemImageView;
        Button incAmount, decAmount;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.item_cart_container);
            titleTextView = itemView.findViewById(R.id.title_item_cart);
            priceTextView = itemView.findViewById(R.id.price_item_cart);
            amountTextView = itemView.findViewById(R.id.amount);
            itemImageView = itemView.findViewById(R.id.item_image);
            incAmount = itemView.findViewById(R.id.increase_btn);
            decAmount = itemView.findViewById(R.id.decrease_btn);

        }
    }
}
