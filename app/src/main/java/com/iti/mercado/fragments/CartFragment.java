package com.iti.mercado.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.iti.mercado.R;
import com.iti.mercado.adapter.CartAdapter;
import com.iti.mercado.model.Cart;
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
import com.iti.mercado.utilities.CountSubPrice;
import com.iti.mercado.utilities.DatabaseCart;
import com.iti.mercado.utilities.DatabaseItemCart;
import com.iti.mercado.utilities.OnRetrieveItem;
import com.iti.mercado.utilities.SwipeToDeleteCallback;

import java.util.ArrayList;


public class CartFragment extends Fragment implements OnRetrieveItem, CountSubPrice {

    private ArrayList<Cart> carts;
    private CartAdapter cartAdapter;
    private RecyclerView recyclerView;
    TextView subTotalText, shippingText, totalText;
    LinearLayout linearLayout;
    private Double subTotal = 0.0;


    @Override
    public void onStart() {
        super.onStart();
        carts = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), carts, this);
        recyclerView.setAdapter(cartAdapter);
        DatabaseCart.getAllItems(carts, () -> {
            for (Cart cart : carts) {
                subCategorySwitch(cart);
            }
        });
//        double sum=0;
//        for (Cart cart: carts){
//            Log.i("TAG", "it's onStart: ");
//            sum=sum+countSubTotal(cart);
//        }


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
        subTotal=0.0;
        // Inflate the layout for this fragment
        // View view = null;
        //  if (carts !=null){
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        subTotalText = view.findViewById(R.id.subtotal);
        shippingText = view.findViewById(R.id.shipping);
        totalText = view.findViewById(R.id.total);
        linearLayout=view.findViewById(R.id.item_cart_container);

        //subTotalText.setText(subTotal + " EGP");
        shippingText.setText("50 EGP");

        enableSwipeToDeleteAndUndo();

//        } else if(carts == null){
//        view = inflater.inflate(R.layout.fragment_cart_empty, container, false);
//       }
        return view;
    }

    public void onRetrieveItems() {
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void countSubTotal(Double price) {
        subTotal = subTotal + price;
        Log.i("TAG", "countSubTotal:priceeeeeeeee " + price);
        subTotalText.setText(subTotal + " EGP");
        totalText.setText((subTotal+50)+" EGP");

    }
//    private double countSubTotal(Cart cart) {
//        double price = Double.parseDouble(cart.getItem().getItem_price());
//        Log.i("TAG", "countSubTotal: jjjjjj");
//        Log.i("TAG", "countSubTotal: price"+price);
//        Log.i("TAG", "countSubTotal: "+cart.getCount());
//        return cart.getCount()*price;
//
//    }


    @Override
    public void onResume() {
        super.onResume();
        subTotal=0.0;
    }
    @Override
    public void onPause() {
        super.onPause();
        subTotal=0.0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subTotal=0.0;
    }

    @Override
    public void onStop() {
        super.onStop();
        subTotal=0.0;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        subTotal=0.0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subTotal=0.0;
    }



    // Swipe part

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Cart cart = cartAdapter.getData().get(position);

                cartAdapter.removeItem(position);


//                Snackbar snackbar = Snackbar
//                        .make(linearLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
//                snackbar.setAction("UNDO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        cartAdapter.restoreItem(cart, position);
//                        recyclerView.scrollToPosition(position);
//                    }
//                });

//                snackbar.setActionTextColor(Color.YELLOW);
//                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}