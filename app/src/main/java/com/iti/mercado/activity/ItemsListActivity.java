package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
import com.iti.mercado.utilities.Constants;
import com.iti.mercado.utilities.Network;
import com.iti.mercado.utilities.OnResponseRetrofit;

import java.util.List;

import retrofit2.Call;

public class ItemsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        recyclerView = findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //Network.parsLaptops(this,recyclerView);
        //Network.parsLaptopBags(this,recyclerView);
        //Network.parsMobiles(this,recyclerView);
        //Network.parsMicrowaves(this,recyclerView);
        //Network.parsBlendersAndMixers(this,recyclerView);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");

        subCategorySwitch(getSubCategoryName(message));
    }

    void subCategorySwitch(String subCategory) {
        switch (subCategory) {
            case "womenClothing":
                Call<List<WomenClothing>> womenClothing = Network.getJsonQ().getWomenClothing();
                createAdapter(womenClothing);
                break;
            case "womenBags":
                Call<List<WomenBags>> womenBags = Network.getJsonQ().getWomenBags();
                createAdapter(womenBags);
                break;
            case "girlsClothing":
                Call<List<KidsClothing>> girlsClothing = Network.getJsonQ().getGirlsClothing();
                createAdapter(girlsClothing);
                break;
            case "girlsShoes":
                Call<List<KidsShoes>> girlsShoes = Network.getJsonQ().getGirlsShoes();
                createAdapter(girlsShoes);
                break;
            case "boysClothing":
                Call<List<KidsClothing>> boysClothing = Network.getJsonQ().getBoysClothing();
                createAdapter(boysClothing);
                break;
            case "boysShoes":
                Call<List<KidsShoes>> boysShoes = Network.getJsonQ().getBoysShoes();
                createAdapter(boysShoes);
                break;
            case "microwaves":
                Call<List<HomeAppliance>> microwaves = Network.getJsonQ().getMicrowaves();
                createAdapter(microwaves);
                break;
            case "blendersAndMixers":
                Call<List<HomeAppliance>> blendersAndMixers = Network.getJsonQ().getBlendersAndMixers();
                createAdapter(blendersAndMixers);
                break;
            case "laptops":
                Call<List<Laptop>> laptops = Network.getJsonQ().getLaptops();
                createAdapter(laptops);
                break;
            case "laptopBags":
                Call<List<LaptopBag>> laptopBags = Network.getJsonQ().getLaptopBags();
                createAdapter(laptopBags);
                break;
            case "mobiles":
                Call<List<Mobile>> mobiles = Network.getJsonQ().getMobiles();
                createAdapter(mobiles);
                break;
            case "tablets":
                Call<List<Mobile>> tablets = Network.getJsonQ().getTablets();
                createAdapter(tablets);
                break;
            case "beautyEquipment":
                Call<List<PersonalCare>> beautyEquipment = Network.getJsonQ().getBeautyEquipment();
                createAdapter(beautyEquipment);
                break;
            case "hairStylers":
                Call<List<PersonalCare>> hairStylers = Network.getJsonQ().getHairStylers();
                createAdapter(hairStylers);
                break;
            case "makeUp":
                Call<List<MakeUp>> makeUp = Network.getJsonQ().getMakeUp();
                createAdapter(makeUp);
                break;
            case "skinCare":
                Call<List<SkinCare>> skinCare = Network.getJsonQ().getSkinCare();
                createAdapter(skinCare);
                break;
        }
    }

    public <K extends Item> void createAdapter(Call<List<K>> call){
        Network.parsJson(call, new OnResponseRetrofit<K>() {
            @Override
            public void onResponse(List<K> items) {
                ItemsAdapter<K> adapter =
                        new ItemsAdapter<K>(ItemsListActivity.this, items);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private String getSubCategoryName(String message){
        Log.i("TAG", "getSubCategoryName: " + message.substring(Constants.BASE_URI.length()));
        return message.substring(Constants.BASE_URI.length());
    }

}