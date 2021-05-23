package com.iti.mercado.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.iti.mercado.R;
import com.iti.mercado.adapter.FavoriteAdapter;
import com.iti.mercado.model.FavoriteItem;
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
import com.iti.mercado.utilities.DatabaseFavorite;
import com.iti.mercado.utilities.DatabaseItem;
import com.iti.mercado.utilities.Network;
import com.iti.mercado.utilities.OnRetrieveItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class FavoriteFragment extends Fragment implements OnRetrieveItem {
    ArrayList<Item> items;
    RecyclerView favoriteRecyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ArrayList<FavoriteItem> favoriteItems = new ArrayList<>();
        items = new ArrayList<>();
        DatabaseFavorite.getAllItems(favoriteItems, () -> {
            for (FavoriteItem favoriteItem : favoriteItems) {
                subCategorySwitch(favoriteItem);
            }
        });
    }

    void subCategorySwitch(FavoriteItem favoriteItem) {
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onRetrieveItems(Item item) {
        items.add(item);
    }
}