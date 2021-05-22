package com.iti.mercado.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.iti.mercado.R;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<FavoriteItem> favoriteItems = new ArrayList<>();
        DatabaseFavorite.getAllItems(favoriteItems, () -> {
            for (FavoriteItem favoriteItem : favoriteItems) {

            }
        });
    }
    void subCategorySwitch(FavoriteItem favoriteItem) {

        switch (favoriteItem.getSubCategory()) {
            case "clothing":
                switch (favoriteItem.getCategory()) {
                    case "Women's Fashion":
                        DatabaseItem.getItemDetails(favoriteItem, WomenClothing.class,
                                this);
                        break;
                    default:
                        DatabaseItem.getItemDetails(favoriteItem, KidsClothing.class,
                                this);
                }
                break;
            case "shoes":
                DatabaseItem.getItemDetails(favoriteItem, KidsShoes.class, this);
                break;
            case "bags":
                DatabaseItem.getItemDetails(favoriteItem, WomenBags.class, this);
                break;
            case "makeUp":
                DatabaseItem.getItemDetails(favoriteItem, MakeUp.class, this);
                break;
            case "skinCare":
                DatabaseItem.getItemDetails(favoriteItem, SkinCare.class, this);
                break;
            case "blendersAndMixers":
                DatabaseItem.getItemDetails(favoriteItem, HomeAppliance.class, this);
                break;
            case "microwaves":
                DatabaseItem.getItemDetails(favoriteItem, HomeAppliance.class, this);
                break;
            case "laptopBags":
                DatabaseItem.getItemDetails(favoriteItem, LaptopBag.class, this);
                break;
            case "laptops":
                DatabaseItem.getItemDetails(favoriteItem, Laptop.class, this);
                break;
            case "mobiles":
                DatabaseItem.getItemDetails(favoriteItem, Mobile.class, this);
                break;
            case "tablets":
                DatabaseItem.getItemDetails(favoriteItem, Mobile.class, this);
                break;
            case "beautyEquipment":
                DatabaseItem.getItemDetails(favoriteItem, PersonalCare.class, this);
                break;
            case "hairStylers":
                DatabaseItem.getItemDetails(favoriteItem, PersonalCare.class, this);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onRetrieveItems(Item item) {

    }
}