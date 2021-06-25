package com.iti.mercado.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iti.mercado.R;
import com.iti.mercado.adapter.FavoriteAdapter;
import com.iti.mercado.model.ItemPath;
import com.iti.mercado.model.HomeAppliance;
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
import com.iti.mercado.utilities.DatabaseFavorite;
import com.iti.mercado.utilities.DatabaseItem;
import com.iti.mercado.utilities.OnRetrieveItem;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment implements OnRetrieveItem {

    private ArrayList<ItemPath> favoriteItems;
    private FavoriteAdapter favoriteAdapter;
    private RecyclerView recyclerView;
    private TextView noItemFoundTextView;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        favoriteItems = new ArrayList<>();
        DatabaseFavorite.getAllItems(favoriteItems, () -> {
            for (ItemPath favoriteItem : favoriteItems) {
                subCategorySwitch(favoriteItem);
            }

            onRetrieveItems();
        });

        favoriteAdapter = new FavoriteAdapter(getActivity(), favoriteItems, this);
        recyclerView.setAdapter(favoriteAdapter);
    }

    void subCategorySwitch(ItemPath favoriteItem) {
        if (favoriteItem.getSubCategory().equals("clothing")) {
            if (favoriteItem.getCategory().equals("Women's Fashion"))
                DatabaseItem.getItemDetails(favoriteItem, WomenClothing.class, this);
            else if (favoriteItem.getCategory().equals("Girl's Fashion") ||
                    favoriteItem.getCategory().equals("boy's fashion"))
                DatabaseItem.getItemDetails(favoriteItem, KidsClothing.class, this);
        } else if (favoriteItem.getSubCategory().equals("shoes"))
            DatabaseItem.getItemDetails(favoriteItem, KidsShoes.class, this);
        else if (favoriteItem.getSubCategory().equals("bags"))
            DatabaseItem.getItemDetails(favoriteItem, WomenBags.class, this);
        else if (favoriteItem.getSubCategory().equals("makeUp"))
            DatabaseItem.getItemDetails(favoriteItem, MakeUp.class, this);
        else if (favoriteItem.getSubCategory().equals("skinCare"))
            DatabaseItem.getItemDetails(favoriteItem, SkinCare.class, this);
        else if (favoriteItem.getSubCategory().equals("microwaves") ||
                favoriteItem.getSubCategory().equals("blendersAndMixers"))
            DatabaseItem.getItemDetails(favoriteItem, HomeAppliance.class, this);
        else if (favoriteItem.getSubCategory().equals("laptopBags"))
            DatabaseItem.getItemDetails(favoriteItem, LaptopBag.class, this);
        else if (favoriteItem.getSubCategory().equals("laptops"))
            DatabaseItem.getItemDetails(favoriteItem, Laptop.class, this);
        else if (favoriteItem.getSubCategory().equals("mobiles") ||
                favoriteItem.getSubCategory().equals("tablets"))
            DatabaseItem.getItemDetails(favoriteItem, Mobile.class, this);
        else if (favoriteItem.getSubCategory().equals("beautyEquipment") ||
                favoriteItem.getSubCategory().equals("hairStylers"))
            DatabaseItem.getItemDetails(favoriteItem, PersonalCare.class, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        noItemFoundTextView = view.findViewById(R.id.noItemFound);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView = view.findViewById(R.id.favorite_recyclerview);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    @Override
    public void onRetrieveItems() {
        favoriteAdapter.notifyDataSetChanged();

        progressBar.setVisibility(View.GONE);

        if (favoriteItems.size() == 0 ) {
            noItemFoundTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noItemFoundTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}