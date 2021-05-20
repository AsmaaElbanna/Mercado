package com.iti.mercado.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    private static JsonQ jsonQ;

    public static JsonQ getJsonQ() {
        if (jsonQ == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URI)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            jsonQ = retrofit.create(JsonQ.class);
        }
        return jsonQ;
    }

    public static <K> void parsJson(Call<List<K>> items,
                                    OnResponseRetrofit<K> onResponseRetrofit) {

        items.enqueue(new Callback<List<K>>() {

            @Override
            public void onResponse(@NotNull Call<List<K>> call,
                                   @NotNull Response<List<K>> response) {

                List<K> items  = response.body();
                onResponseRetrofit.onResponse(items);
            }
            @Override
            public void onFailure(@NotNull Call<List<K>> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
