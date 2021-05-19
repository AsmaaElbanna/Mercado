package com.iti.mercado.utilities;

import com.iti.mercado.model.Category;
import com.iti.mercado.model.KidsClothing;
import com.iti.mercado.model.KidsShoes;
import com.iti.mercado.model.Laptop;
import com.iti.mercado.model.LaptopBag;
import com.iti.mercado.model.Mobile;
import com.iti.mercado.model.WomenBags;
import com.iti.mercado.model.WomenClothing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonQ {

    @GET("laptops")
    Call<List<Laptop>> getLaptops();

    @GET("laptopBags")
    Call<List<LaptopBag>> getLaptopBags();

    @GET("mobiles")
    Call<List<Mobile>> getMobiles();

    @GET("tablets")
    Call<List<Mobile>> getTablets();

    @GET("girlsClothing")
    Call<List<KidsClothing>> getGirlsClothing();

    @GET("girlsShoes")
    Call<List<KidsShoes>> getGirlsShoes();

    @GET("boysClothing")
    Call<List<KidsClothing>> getBoysClothing();

    @GET("boysShoes")
    Call<List<KidsShoes>> getBoysShoes();

    @GET("womenClothing")
    Call<List<WomenClothing>> getWomenClothing();

    @GET("womenBags")
    Call<List<WomenBags>> getWomenBags();

    @GET("categories")
    Call<List<Category>> getCategories();
}
