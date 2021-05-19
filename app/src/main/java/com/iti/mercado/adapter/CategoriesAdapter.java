package com.iti.mercado.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.iti.mercado.R;
import com.iti.mercado.model.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final Context context;
    private final List<Category> categories;
    private final LinearLayout subCategoriesLinearLayout;

    private int lastCategoryClicked = -1;
    private TextView lastCategoryTitleClicked;
    private CardView lastCategoryCardViewClicked;

    public CategoriesAdapter(Context context, List<Category> categories,
                             LinearLayout subCategoriesLinearLayout) {
        this.context = context;
        this.categories = categories;
        this.subCategoriesLinearLayout = subCategoriesLinearLayout;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_single_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        holder.categoryTitle.setText(categories.get(position).getCategory_name());
        Glide.with(context).load(categories.get(position).getCategory_icon())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.categoryImage);

        holder.categoryImage.setOnClickListener(v -> {
            onClickOnCategory(position);

            if(lastCategoryTitleClicked != null) {
                lastCategoryTitleClicked.setTextColor(context.getResources().
                        getColor(R.color.black));
                lastCategoryCardViewClicked.setCardBackgroundColor(context.getResources().
                        getColor(R.color.baby_green));
            }

            holder.categoryTitle.setTextColor(context.getResources().
                    getColor(R.color.category_onClick));
            holder.categoryCardView.setCardBackgroundColor(context.getResources().
                    getColor(R.color.white));

            lastCategoryTitleClicked = holder.categoryTitle;
            lastCategoryCardViewClicked = holder.categoryCardView;
        });
    }

    void onClickOnCategory(int position) {
        if (position != lastCategoryClicked) {
            subCategoriesLinearLayout.removeAllViews();

            for (int i = 0; i < categories.get(position).getSubCategories().length; i++) {
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                TextView textView = (TextView) inflater
                        .inflate(R.layout.sub_category_template, null);
                textView.setText(categories.get(position)
                        .getSubCategories()[i].getSubcategory_name());

                Transition transition = new Slide(Gravity.START);
                transition.setDuration(600);
                TransitionManager.beginDelayedTransition(subCategoriesLinearLayout, transition);
                subCategoriesLinearLayout.addView(textView);
            }
            lastCategoryClicked = position;
        }
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryTitle;
        public ImageButton categoryImage;
        public CardView categoryCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryCardView = itemView.findViewById(R.id.categoryCardView);
        }
    }
}