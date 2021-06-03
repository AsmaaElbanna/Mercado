package com.iti.mercado.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mercado.R;
import com.iti.mercado.adapter.CartAdapter;
import com.iti.mercado.adapter.FavoriteAdapter;
import com.iti.mercado.model.Cart;
import com.iti.mercado.model.FavoriteItem;
import com.iti.mercado.model.HomeAppliance;
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
import com.iti.mercado.utilities.DatabaseCart;
import com.iti.mercado.utilities.DatabaseFavorite;
import com.iti.mercado.utilities.DatabaseItem;
import com.iti.mercado.utilities.DatabaseItemCart;
import com.iti.mercado.utilities.OnRetrieveItem;

import java.util.ArrayList;


public class CartFragment extends Fragment implements OnRetrieveItem {

    private ArrayList<Cart> carts;
    private CartAdapter cartAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onStart() {
        super.onStart();
        carts = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), carts);
        recyclerView.setAdapter(cartAdapter);
        DatabaseCart.getAllItems(carts, () -> {
            for (Cart cart : carts) {
                subCategorySwitch(cart);
            }
        });
    }


    private void subCategorySwitch(Cart cart) {
        if (cart.getSubCategory().equals("clothing")) {
            if (cart.getCategory().equals("Women's Fashion"))
                DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this);
            else if (cart.getCategory().equals("Girl's Fashion") ||
                    cart.getCategory().equals("boy's fashion"))
                DatabaseItemCart.getItemDetails(cart, KidsClothing.class, this);
        } else if (cart.getSubCategory().equals("shoes"))
            DatabaseItemCart.getItemDetails(cart, KidsShoes.class, this);
        else if (cart.getSubCategory().equals("bags"))
            DatabaseItemCart.getItemDetails(cart, WomenBags.class, this);
        else if (cart.getSubCategory().equals("makeUp"))
            DatabaseItemCart.getItemDetails(cart, MakeUp.class, this);
        else if (cart.getSubCategory().equals("skinCare"))
            DatabaseItemCart.getItemDetails(cart, SkinCare.class, this);
        else if (cart.getSubCategory().equals("microwaves") ||
                cart.getSubCategory().equals("blendersAndMixers"))
            DatabaseItemCart.getItemDetails(cart, HomeAppliance.class, this);
        else if (cart.getSubCategory().equals("laptopBags"))
            DatabaseItemCart.getItemDetails(cart, LaptopBag.class, this);
        else if (cart.getSubCategory().equals("laptops"))
            DatabaseItemCart.getItemDetails(cart, Laptop.class, this);
        else if (cart.getSubCategory().equals("mobiles") ||
                cart.getSubCategory().equals("tablets"))
            DatabaseItemCart.getItemDetails(cart, Mobile.class, this);
        else if (cart.getSubCategory().equals("beautyEquipment") ||
                cart.getSubCategory().equals("hairStylers"))
            DatabaseItemCart.getItemDetails(cart, PersonalCare.class, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        Log.i("TAG", "onCreateView:Cart ");

        recyclerView = view.findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void onRetrieveItems() {
        cartAdapter.notifyDataSetChanged();
    }
}