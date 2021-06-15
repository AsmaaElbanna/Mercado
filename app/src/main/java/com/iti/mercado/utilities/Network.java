package com.iti.mercado.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

                List<K> items = response.body();
                onResponseRetrofit.onResponse(items);
            }

            @Override
            public void onFailure(@NotNull Call<List<K>> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isInternetConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isConnectedToWorld(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        return (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                && isInternetConnected();
    }
}
