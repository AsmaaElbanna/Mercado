package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.iti.mercado.R;
import com.iti.mercado.adapter.ItemsAdapter;
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
import com.iti.mercado.utilities.BottomSheetFilterListener;
import com.iti.mercado.utilities.Constants;
import com.iti.mercado.utilities.MyBottomSheetDialogFilter;
import com.iti.mercado.utilities.Network;
import com.iti.mercado.utilities.OnResponseRetrofit;
import com.iti.mercado.utilities.ReloadItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;

public class ItemsListActivity extends AppCompatActivity implements BottomSheetFilterListener {


    private ImageView backArrow;
    private RecyclerView recyclerView;
    private String category, sub_category;
    private ImageView filterImageView;

    private List<Item> itemsFilter;
    private List<Item> items;
    private ItemsAdapter<Item> adapter;
    private Bundle bundle;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        progressBar = findViewById(R.id.progressBar);

        backArrow = findViewById(R.id.back_button);
        filterImageView = (ImageView) findViewById(R.id.filter);
        recyclerView = findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        bundle = getIntent().getExtras();
        String message = bundle.getString("message");

        subCategorySwitch(getSubCategoryName(message));

        backArrow.setOnClickListener(v -> {
            finish();
        });
    }

    void subCategorySwitch(String subCategory) {
        switch (subCategory) {
            case "womenClothing":
                category = "Women's Fashion";
                sub_category = "clothing";
                Call<List<WomenClothing>> womenClothing = Network.getJsonQ().getWomenClothing();
                createAdapter(womenClothing);
                break;
            case "womenBags":
                category = "Women's Fashion";
                sub_category = "bags";
                Call<List<WomenBags>> womenBags = Network.getJsonQ().getWomenBags();
                createAdapter(womenBags);
                break;
            case "girlsClothing":
                category = "Girl's Fashion";
                sub_category = "clothing";
                Call<List<KidsClothing>> girlsClothing = Network.getJsonQ().getGirlsClothing();
                createAdapter(girlsClothing);
                break;
            case "girlsShoes":
                category = "Girl's Fashion";
                sub_category = "shoes";
                Call<List<KidsShoes>> girlsShoes = Network.getJsonQ().getGirlsShoes();
                createAdapter(girlsShoes);
                break;
            case "boysClothing":
                category = "boy's fashion";
                sub_category = "clothing";
                Call<List<KidsClothing>> boysClothing = Network.getJsonQ().getBoysClothing();
                createAdapter(boysClothing);
                break;
            case "boysShoes":
                category = "boy's fashion";
                sub_category = "shoes";
                Call<List<KidsShoes>> boysShoes = Network.getJsonQ().getBoysShoes();
                createAdapter(boysShoes);
                break;
            case "microwaves":
                category = "homeAppliances";
                sub_category = "microwaves";
                Call<List<HomeAppliance>> microwaves = Network.getJsonQ().getMicrowaves();
                createAdapter(microwaves);
                break;
            case "blendersAndMixers":
                category = "homeAppliances";
                sub_category = "blendersAndMixers";
                Call<List<HomeAppliance>> blendersAndMixers = Network.getJsonQ().getBlendersAndMixers();
                createAdapter(blendersAndMixers);
                break;
            case "laptops":
                category = "laptopAndPC";
                sub_category = "laptops";
                Call<List<Laptop>> laptops = Network.getJsonQ().getLaptops();
                createAdapter(laptops);
                break;
            case "laptopBags":
                category = "laptopAndPC";
                sub_category = "laptopBags";
                Call<List<LaptopBag>> laptopBags = Network.getJsonQ().getLaptopBags();
                createAdapter(laptopBags);
                break;
            case "mobiles":
                category = "mobilesAndTablets";
                sub_category = "mobiles";
                Call<List<Mobile>> mobiles = Network.getJsonQ().getMobiles();
                createAdapter(mobiles);
                break;
            case "tablets":
                category = "mobilesAndTablets";
                sub_category = "tablets";
                Call<List<Mobile>> tablets = Network.getJsonQ().getTablets();
                createAdapter(tablets);
                break;
            case "beautyEquipment":
                category = "personalCare";
                sub_category = "beautyEquipment";
                Call<List<PersonalCare>> beautyEquipment = Network.getJsonQ().getBeautyEquipment();
                createAdapter(beautyEquipment);
                break;
            case "hairStylers":
                category = "personalCare";
                sub_category = "hairStylers";
                Call<List<PersonalCare>> hairStylers = Network.getJsonQ().getHairStylers();
                createAdapter(hairStylers);
                break;
            case "makeUp":
                category = "beautyCare";
                sub_category = "makeUp";
                Call<List<MakeUp>> makeUp = Network.getJsonQ().getMakeUp();
                createAdapter(makeUp);
                break;
            case "skinCare":
                category = "beautyCare";
                sub_category = "skinCare";
                Call<List<SkinCare>> skinCare = Network.getJsonQ().getSkinCare();
                createAdapter(skinCare);
                break;
        }
    }

    public <K extends Item> void createAdapter(Call<List<K>> call) {
        Network.parsJson(call, new OnResponseRetrofit<K>() {
            @Override
            public void onResponse(List<K> items) {

                ItemsListActivity.this.items = new ArrayList<>((List<Item>) items);
                ItemsListActivity.this.itemsFilter = (List<Item>) items;      // itemsFilter refer to items

                adapter = new ItemsAdapter(ItemsListActivity.this, items, category, sub_category);
                recyclerView.setAdapter(adapter);
                filterImageView.setClickable(true);
                filterImageView.setOnClickListener(v -> {
                    MyBottomSheetDialogFilter bottomSheetDialogFilter = new MyBottomSheetDialogFilter();
                    bottomSheetDialogFilter.show(getSupportFragmentManager(), "bottomSheet");
                    bundle.putString("Category ItemListActivity", category);
                    bundle.putString("SubCategory ItemListActivity", sub_category);
                    bottomSheetDialogFilter.setArguments(bundle);
                });

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private String getSubCategoryName(String message) {
        Log.i("TAG", "getSubCategoryName: " + message.substring(Constants.BASE_URI.length()));
        return message.substring(Constants.BASE_URI.length());
    }

    @Override
    public void onApplyFilterClicked(HashSet<String> filterBrands, List<Double> filterPrice, int flag) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (filterBrands.contains("All brands") && filterPrice.isEmpty()) {
                itemsFilter = items;
            } else {
                itemsFilter = items.stream().filter(item -> {
                    for (String filterValue : filterBrands) {
                        if (item.getBrand().equals(filterValue)) {
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toList());
            } // end else

            // price filter test

            if (!filterPrice.isEmpty()) {
                switch (flag) {
                    case 0:
                        itemsFilter = items.stream().filter(item -> {
                            for (double filterValue : filterPrice) {
                                double price = Double.parseDouble((item.getItem_price()));
                                if (price < filterPrice.get(0)) {
                                    return true;
                                }
                            }
                            return false;
                        }).collect(Collectors.toList());
                        break;
                    case 1:
                        itemsFilter = items.stream().filter(item -> {
                            for (double filterValue : filterPrice) {
                                double price = Double.parseDouble(item.getItem_price());
                                Log.i("TAG", "onApplyFilterClicked: " + filterPrice.get(1));
                                if (price > filterPrice.get(0) && price < filterPrice.get(1)) {
                                    return true;
                                }
                            }
                            return false;
                        }).collect(Collectors.toList());
                        break;
                    case 2:
                        itemsFilter = items.stream().filter(item -> {
                            for (double filterValue : filterPrice) {
                                double price = Double.parseDouble(item.getItem_price());
                                if (price > filterPrice.get(0)) {
                                    return true;
                                }
                            }
                            return false;
                        }).collect(Collectors.toList());
                        break;
                    default:
                        Log.i("TAG", "onApplyFilterClicked: ");
                }
            }


            // end test
        }
        adapter.setItems(itemsFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (adapter != null) {
            if (ReloadItem.adapterName.equals("ItemsAdapter"))
                adapter.notifyItemChanged(ReloadItem.itemNumberInAdapter);
        }

        ReloadItem.adapterName = "";
    }
}