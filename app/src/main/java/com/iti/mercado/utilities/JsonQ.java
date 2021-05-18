package com.iti.mercado.utilities;

import com.iti.mercado.model.Laptop;
import com.iti.mercado.model.LaptopBag;
import com.iti.mercado.model.Mobile;

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


}
