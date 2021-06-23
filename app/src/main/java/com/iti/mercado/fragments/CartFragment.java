package com.iti.mercado.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iti.mercado.R;
import com.iti.mercado.activity.CheckoutActivity;
import com.iti.mercado.adapter.CartAdapter;
import com.iti.mercado.model.Cart;
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
    private TextView subTotalText, shippingText, totalText;
    private LinearLayout linearLayout;
    private Double subTotal;
    private Button checkoutButton;
    private int itemRetrieved;

    @Override
    public void onStart() {
        super.onStart();
        carts = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), carts, this);
        recyclerView.setAdapter(cartAdapter);
        itemRetrieved = 0;
        subTotal = 0.0;
        DatabaseCart.getAllItems(carts, () -> {
            for (Cart cart : carts) {
                subCategorySwitch(cart);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(cartAdapter);
    }
    private void subCategorySwitch(Cart cart) {
        if (cart.getSubCategory().equals("clothing")) {
            if (cart.getCategory().equals("Women's Fashion"))
                DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
            else if (cart.getCategory().equals("Girl's Fashion") ||
                    cart.getCategory().equals("boy's fashion"))
                DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
        } else if (cart.getSubCategory().equals("shoes"))
            DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
        else if (cart.getSubCategory().equals("bags"))
            DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
        else if (cart.getSubCategory().equals("makeUp"))
            DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
        else if (cart.getSubCategory().equals("skinCare"))
            DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
        else if (cart.getSubCategory().equals("microwaves") ||
                cart.getSubCategory().equals("blendersAndMixers"))
            DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
        else if (cart.getSubCategory().equals("laptopBags"))
            DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
        else if (cart.getSubCategory().equals("laptops"))
            DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
        else if (cart.getSubCategory().equals("mobiles") ||
                cart.getSubCategory().equals("tablets"))
            DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
        else if (cart.getSubCategory().equals("beautyEquipment") ||
                cart.getSubCategory().equals("hairStylers"))
            DatabaseItemCart.getItemDetails(cart, WomenClothing.class, this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View view = null;
        //  if (carts !=null){
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        checkoutButton = view.findViewById(R.id.checkout);
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
        itemRetrieved++;
        if (itemRetrieved == carts.size()) {
            this.checkoutButton.setOnClickListener(v -> {
                if (carts.size() != 0) {
                    Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                    intent.putExtra("carts", carts);
                    intent.putExtra("subTotal", subTotal);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void countSubTotal(Double price) {
        subTotal = subTotal + price;
        subTotalText.setText(subTotal + " EGP");
        totalText.setText((subTotal+50)+" EGP");

        if (carts.size() == 0) {
            totalText.setText(String.valueOf(0));
        }
    }
     // Swipe part

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                cartAdapter.removeItem(position);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}