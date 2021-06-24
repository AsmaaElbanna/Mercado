package com.iti.mercado.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iti.mercado.R;
import com.iti.mercado.RealtimeDatabase.DatabaseItems;
import com.iti.mercado.activity.SearchActivity;
import com.iti.mercado.adapter.FavoriteAdapter;
import com.iti.mercado.adapter.NewArrivalAdapter;
import com.iti.mercado.adapter.OfferAdapter;
import com.iti.mercado.model.FlashSale;
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
import com.iti.mercado.utilities.DatabaseFlashSale;
import com.iti.mercado.utilities.DatabaseItem;
import com.iti.mercado.utilities.OnRetrieveFlashSale;
import com.iti.mercado.utilities.OnRetrieveItem;
import com.iti.mercado.utilities.ReloadItem;

import java.util.ArrayList;
import java.util.List;


public class ShopFragment extends Fragment implements OnRetrieveFlashSale {

    private ImageSlider imageSlider;
    private List<ItemPath> newArrivalItems;
    private List<ItemPath> offersItems;

    private NewArrivalAdapter newArrivalAdapter;
    private OfferAdapter offerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        imageSlider = view.findViewById(R.id.slider);

        TextView searchEditText = view.findViewById(R.id.search);
        searchEditText.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                            getActivity(),
                            searchEditText,
                            ViewCompat.getTransitionName(searchEditText));
                    getActivity().startActivity(intent, activityOptionsCompat.toBundle());
        });
        RecyclerView newArrivalRecyclerview = view.findViewById(R.id.new_arrival_recyclerview);
        RecyclerView offerRecyclerview = view.findViewById(R.id.offer_recyclerview);

        newArrivalRecyclerview.setHasFixedSize(false);
        offerRecyclerview.setHasFixedSize(false);

        LinearLayoutManager newArrivalLayoutManager = new LinearLayoutManager(getActivity());
        newArrivalLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        LinearLayoutManager offerLayoutManager = new LinearLayoutManager(getActivity());
        offerLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        newArrivalRecyclerview.setLayoutManager(newArrivalLayoutManager);
        offerRecyclerview.setLayoutManager(offerLayoutManager);

        DatabaseFlashSale.getImages(this);

        DatabaseItems.getItems("new arrival", itemsPath -> {
            this.newArrivalItems = itemsPath;

            newArrivalAdapter = new NewArrivalAdapter(getActivity(), newArrivalItems);
            newArrivalRecyclerview.setAdapter(newArrivalAdapter);

            for (ItemPath favoriteItem : itemsPath) {
                subCategorySwitch(favoriteItem, () -> {
                    this.newArrivalAdapter.notifyDataSetChanged();
                });
            }
        });

        DatabaseItems.getItems("offers", itemsPath -> {
            this.offersItems = itemsPath;

            offerAdapter = new OfferAdapter(getActivity(), offersItems);
            offerRecyclerview.setAdapter(offerAdapter);

            for (ItemPath favoriteItem : itemsPath) {
                subCategorySwitch(favoriteItem, () -> {
                    this.offerAdapter.notifyDataSetChanged();
                });
            }
        });

        return view;
    }

    void subCategorySwitch(ItemPath itemPath, OnRetrieveItem onRetrieveItem) {
        if (itemPath.getSubCategory().equals("clothing")) {
            if (itemPath.getCategory().equals("Women's Fashion"))
                DatabaseItem.getItemDetails(itemPath, WomenClothing.class, onRetrieveItem);
            else if (itemPath.getCategory().equals("Girl's Fashion") ||
                    itemPath.getCategory().equals("boy's fashion"))
                DatabaseItem.getItemDetails(itemPath, KidsClothing.class, onRetrieveItem);
        } else if (itemPath.getSubCategory().equals("shoes"))
            DatabaseItem.getItemDetails(itemPath, KidsShoes.class, onRetrieveItem);
        else if (itemPath.getSubCategory().equals("bags"))
            DatabaseItem.getItemDetails(itemPath, WomenBags.class, onRetrieveItem);
        else if (itemPath.getSubCategory().equals("makeUp"))
            DatabaseItem.getItemDetails(itemPath, MakeUp.class, onRetrieveItem);
        else if (itemPath.getSubCategory().equals("skinCare"))
            DatabaseItem.getItemDetails(itemPath, SkinCare.class, onRetrieveItem);
        else if (itemPath.getSubCategory().equals("microwaves") ||
                itemPath.getSubCategory().equals("blendersAndMixers"))
            DatabaseItem.getItemDetails(itemPath, HomeAppliance.class, onRetrieveItem);
        else if (itemPath.getSubCategory().equals("laptopBags"))
            DatabaseItem.getItemDetails(itemPath, LaptopBag.class, onRetrieveItem);
        else if (itemPath.getSubCategory().equals("laptops"))
            DatabaseItem.getItemDetails(itemPath, Laptop.class, onRetrieveItem);
        else if (itemPath.getSubCategory().equals("mobiles") ||
                itemPath.getSubCategory().equals("tablets"))
            DatabaseItem.getItemDetails(itemPath, Mobile.class, onRetrieveItem);
        else if (itemPath.getSubCategory().equals("beautyEquipment") ||
                itemPath.getSubCategory().equals("hairStylers"))
            DatabaseItem.getItemDetails(itemPath, PersonalCare.class, onRetrieveItem);
    }

    @Override
    public void getFlashSaleList(List<FlashSale> flashSales) {
        List<SlideModel> slideModels = new ArrayList<>();
        for (FlashSale flashSale: flashSales) {
            slideModels.add(new SlideModel(flashSale.getImage()));
        }
        imageSlider.setImageList(slideModels, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (offerAdapter != null) {
            if (ReloadItem.adapterName.equals("OfferAdapter"))
                offerAdapter.notifyItemChanged(ReloadItem.itemNumberInAdapter);
        }

        if (newArrivalAdapter != null) {
            if (ReloadItem.adapterName.equals("NewArrivalAdapter"))
                newArrivalAdapter.notifyItemChanged(ReloadItem.itemNumberInAdapter);
        }

        ReloadItem.adapterName = "";
    }
}