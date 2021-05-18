package com.iti.mercado.utilities;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iti.mercado.adapter.LaptopAdapter;
import com.iti.mercado.adapter.LaptopBagAdapter;
import com.iti.mercado.adapter.MobileAdapter;
import com.iti.mercado.model.Laptop;
import com.iti.mercado.model.LaptopBag;
import com.iti.mercado.model.Mobile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    public static void parsMobiles(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<Mobile>> call = q.getMobiles();
        call.enqueue(new Callback<List<Mobile>>() {

            @Override
            public void onResponse(Call<List<Mobile>> call, Response<List<Mobile>> response) {

                List<Mobile> list  = response.body();

                Log.i("TAG",response.body().toString());

                MobileAdapter adapter = new MobileAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Mobile>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void parsTablets(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<Mobile>> call = q.getTablets();
        call.enqueue(new Callback<List<Mobile>>() {

            @Override
            public void onResponse(Call<List<Mobile>> call, Response<List<Mobile>> response) {

                List<Mobile> list  = response.body();

                Log.i("TAG",response.body().toString());

                MobileAdapter adapter = new MobileAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Mobile>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void parsLaptopBags(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<LaptopBag>> call = q.getLaptopBags();
        call.enqueue(new Callback<List<LaptopBag>>() {

            @Override
            public void onResponse(Call<List<LaptopBag>> call, Response<List<LaptopBag>> response) {

                List<LaptopBag> list  = response.body();

                Log.i("TAG",response.body().toString());

                LaptopBagAdapter adapter = new LaptopBagAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<LaptopBag>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void parsLaptops(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<Laptop>> call = q.getLaptops();
        call.enqueue(new Callback<List<Laptop>>() {

            @Override
            public void onResponse(Call<List<Laptop>> call, Response<List<Laptop>> response) {

                List<Laptop> list  = response.body();

                Log.i("TAG",response.body().toString());

                LaptopAdapter adapter = new LaptopAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Laptop>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
