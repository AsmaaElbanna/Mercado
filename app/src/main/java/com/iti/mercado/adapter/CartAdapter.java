package com.iti.mercado.adapter;

import android.content.Context;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final List<Cart> carts;
    private CountSubPrice countSubPrice;

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

        if (carts.get(position).getItem() != null) {

            holder.titleTextView.setText(carts.get(position).getItem().getItem_title());
            holder.priceTextView.setText(carts.get(position).getItem().getItem_price() + " EGP");
            holder.amountTextView.setText(carts.get(position).getCount() + "");
            // count=Integer.parseInt((String) holder.amountTextView.getText());
            Glide.with(context).load(carts.get(position).getItem().getItem_image())
                    //.apply(new RequestOptions().override(100,100))
                    .placeholder(R.color.white)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.itemImageView);

            if (countSubPrice != null) {
                holder.incAmount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = carts.get(position).getCount();
                        count++;
                        DatabaseCart.updateCount(count, carts.get(position).getItemId());
                        holder.amountTextView.setText(String.valueOf(count));
                        countSubPrice.countSubTotal(Double.parseDouble(carts.get(position).getItem().getItem_price()));
                        carts.get(position).setCount(carts.get(position).getCount() + 1);
                    }
                });

                holder.decAmount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = carts.get(position).getCount();
                        if (count > 1) {
                            count--;
                            carts.get(position).setCount(count);
                            DatabaseCart.updateCount(count, carts.get(position).getItemId());
                            holder.amountTextView.setText(String.valueOf(count));
                            countSubPrice.countSubTotal(-Double.parseDouble(carts.get(position).getItem().getItem_price()));
                        }
                    }
                });
            } else {
                holder.incAmount.setVisibility(View.INVISIBLE);
                holder.decAmount.setVisibility(View.INVISIBLE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return carts.size();
    }

    public void removeItem(int position) {
        Cart cart = carts.get(position);
        double price = Double.parseDouble(carts.get(position).getItem().getItem_price());
        double subTotal = price * carts.get(position).getCount();
        DatabaseCart.delete(cart, () -> {
            carts.remove(position);
            notifyItemRemoved(position);
            countSubPrice.countSubTotal(-subTotal);
        });
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
