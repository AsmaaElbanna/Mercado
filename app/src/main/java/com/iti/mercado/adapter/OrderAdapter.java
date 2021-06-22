package com.iti.mercado.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mercado.R;
import com.iti.mercado.activity.CheckoutActivity;
import com.iti.mercado.activity.DetailsItemLaptopActivity;
import com.iti.mercado.model.Order;
import com.iti.mercado.model.OrderItem;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private  Context context;
     List<Order>orders;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.codeTextView.setText("#Order"+orders.get(position).getId());
        holder.priceTextView.setText((int) orders.get(position).getTotalPrice()+" EGP");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date((orders.get(position).getTimestamp())*1000));
        holder.timeTextView.setText(dateString+" ");

        holder.detailsTextView.setOnClickListener(v -> {
         Intent intent = new Intent(context, CheckoutActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView codeTextView,timeTextView,priceTextView,detailsTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codeTextView=itemView.findViewById(R.id.order_code);
           timeTextView=itemView.findViewById(R.id.order_time);
           priceTextView=itemView.findViewById(R.id.order_total_price);
           detailsTextView = itemView.findViewById(R.id.view_details);
        }
    }
}
