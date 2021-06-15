package com.iti.mercado.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mercado.R;
import com.iti.mercado.model.Cart;
import com.iti.mercado.utilities.SendDataToFragment;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {

    private Context context;
    private List<String> brands;
    private SendDataToFragment sendDataToFragment;

    public BrandAdapter(Context context, List<String> brands,SendDataToFragment sendDataToFragment) {
        this.context = context;
        this.brands = brands;
        this.sendDataToFragment=sendDataToFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_single_brand,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.brandCheckBox.setText(brands.get(position));

    holder.brandCheckBox.setOnClickListener(v -> {

        String text = brands.get(position);
        sendDataToFragment.sendTextFilter(text);

    });
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox brandCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandCheckBox=itemView.findViewById(R.id.brand);
        }
    }
}
