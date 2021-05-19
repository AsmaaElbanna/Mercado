package com.iti.mercado.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iti.mercado.adapter.CategoriesAdapter;
import com.iti.mercado.adapter.KidsClothingAdapter;
import com.iti.mercado.adapter.KidsShoesAdapter;
import com.iti.mercado.adapter.LaptopAdapter;
import com.iti.mercado.adapter.LaptopBagAdapter;
import com.iti.mercado.adapter.MobileAdapter;
import com.iti.mercado.adapter.WomenBagsAdapter;
import com.iti.mercado.adapter.WomenClothingAdapter;
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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {


    public static void parsCategories(Context context , RecyclerView recyclerView,
                                      LinearLayout subCategoriesLinearLayout) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<Category>> call = q.getCategories();
        call.enqueue(new Callback<List<Category>>() {

            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> list  = response.body();
                Log.i("TAG",response.body().toString());

                CategoriesAdapter adapter = new CategoriesAdapter(context, list,
                        subCategoriesLinearLayout);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

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

    public static void parsGirlsClothing(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<KidsClothing>> call = q.getGirlsClothing();
        call.enqueue(new Callback<List<KidsClothing>>() {

            @Override
            public void onResponse(Call<List<KidsClothing>> call, Response<List<KidsClothing>> response) {

                List<KidsClothing> list  = response.body();

                Log.i("TAG",response.body().toString());

                KidsClothingAdapter adapter = new KidsClothingAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<KidsClothing>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void parsGirlsShoes(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<KidsShoes>> call = q.getGirlsShoes();
        call.enqueue(new Callback<List<KidsShoes>>() {

            @Override
            public void onResponse(Call<List<KidsShoes>> call, Response<List<KidsShoes>> response) {
                List<KidsShoes> list  = response.body();
                Log.i("TAG",response.body().toString());

                KidsShoesAdapter adapter = new KidsShoesAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<KidsShoes>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void parsBoysClothing(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<KidsClothing>> call = q.getGirlsClothing();
        call.enqueue(new Callback<List<KidsClothing>>() {
            @Override
            public void onResponse(Call<List<KidsClothing>> call, Response<List<KidsClothing>> response) {

                List<KidsClothing> list  = response.body();
                Log.i("TAG",response.body().toString());

                KidsClothingAdapter adapter = new KidsClothingAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<KidsClothing>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void parsBoysShoes(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<KidsShoes>> call = q.getGirlsShoes();
        call.enqueue(new Callback<List<KidsShoes>>() {

            @Override
            public void onResponse(Call<List<KidsShoes>> call, Response<List<KidsShoes>> response) {
                List<KidsShoes> list  = response.body();
                Log.i("TAG",response.body().toString());

                KidsShoesAdapter adapter = new KidsShoesAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<KidsShoes>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void parsWomenClothing(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<WomenClothing>> call = q.getWomenClothing();
        call.enqueue(new Callback<List<WomenClothing>>() {

            @Override
            public void onResponse(Call<List<WomenClothing>> call, Response<List<WomenClothing>> response) {
                List<WomenClothing> list  = response.body();
                Log.i("TAG",response.body().toString());

                WomenClothingAdapter adapter = new WomenClothingAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<WomenClothing>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void parsWomenBags(Context context , RecyclerView recyclerView) {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonQ q = retrofit.create(JsonQ.class);
        Call<List<WomenBags>> call = q.getWomenBags();
        call.enqueue(new Callback<List<WomenBags>>() {

            @Override
            public void onResponse(Call<List<WomenBags>> call, Response<List<WomenBags>> response) {
                List<WomenBags> list  = response.body();
                Log.i("TAG",response.body().toString());

                WomenBagsAdapter adapter = new WomenBagsAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<WomenBags>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
