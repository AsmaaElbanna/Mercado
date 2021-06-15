package com.iti.mercado.utilities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.iti.mercado.R;
import com.iti.mercado.adapter.BrandAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MyBottomSheetDialogFilter extends BottomSheetDialogFragment implements SendDataToFragment {

    private List<String> brands;
    private RecyclerView recyclerView;
    private CheckBox filterPriceOneCheckBox, filterPriceTwoCheckBox, filterPriceThreeCheckBox;
    private Button applyFilterButton;
    private BottomSheetFilterListener filterListener;
    private String category, sub_category;

    private HashSet<String> filterValuesHashSet;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        brands = new ArrayList<>();
        filterValuesHashSet = new HashSet<>();
                category = getArguments().getString("Category ItemListActivity");
        sub_category = getArguments().getString("SubCategory ItemListActivity");

        View view = inflater.inflate(R.layout.bottom_sheet_filter, container, false);
        recyclerView = view.findViewById(R.id.list_brands);
        filterPriceOneCheckBox = view.findViewById(R.id.filter_price_one);
        filterPriceTwoCheckBox = view.findViewById(R.id.filter_price_two);
        filterPriceThreeCheckBox = view.findViewById(R.id.filter_price_three);
        applyFilterButton = view.findViewById(R.id.apply_filter);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BrandAdapter brandAdapter = new BrandAdapter(getActivity(), brands,this);
        recyclerView.setAdapter(brandAdapter);
        setFilterValues();

        applyFilterButton.setOnClickListener(v -> {
            filterListener.onApplyFilterClicked(filterValuesHashSet);
            dismiss();
        });

//        filterPriceOneCheckBox.setOnClickListener(v -> {
//            if (filterPriceOneCheckBox.isChecked()) {
//                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
//            } else {
//
//            }
//        });

        return view;
    }

    public void setFilterValues() {

        if (category == "Women's Fashion" && sub_category == "clothing") {

            brands.add("All brands");
            brands.add("Zara");
            brands.add("Shein");
            brands.add("Defacto");

            filterPriceOneCheckBox.setText("Less than 300 EGP");
            filterPriceTwoCheckBox.setText("300 EGP - 600 EGP");
            filterPriceThreeCheckBox.setText("+ 600 EGP");
        } else if (category == "Women's Fashion" && sub_category == "bags") {

            brands.add("All brands");
            brands.add("Shein");
            brands.add("Generic");

            filterPriceOneCheckBox.setText("Less than 400 EGP");
            filterPriceTwoCheckBox.setText("400 EGP - 700 EGP");
            filterPriceThreeCheckBox.setText("+ 700 EGP");
        } else if ((category == "Girl's Fashion" && sub_category == "clothing") ||
                (category == "Girl's Fashion" && sub_category == "shoes") ||
                (category == "boy's fashion" && sub_category == "clothing") ||
                (category == "boy's fashion" && sub_category == "shoes")) {

            brands.add("All brands");
            brands.add("Zara");
            brands.add("Defacto");

            filterPriceOneCheckBox.setText("Less than 500 EGP");
            filterPriceTwoCheckBox.setText("+ 500 EGP");
            filterPriceThreeCheckBox.setVisibility(View.GONE);
        } else if (category == "personalCare" && sub_category == "beautyEquipment") {

            brands.add("All brands");
            brands.add("Hapilin");

            filterPriceOneCheckBox.setText("Less than 400 EGP");
            filterPriceTwoCheckBox.setText("400 EGP - 1000 EGP");
            filterPriceThreeCheckBox.setText("+ 1000 EGP");
        } else if (category == "personalCare" && sub_category == "hairStylers") {

            brands.add("All brands");
            brands.add("Mienta");

            filterPriceOneCheckBox.setText("Less than 400 EGP");
            filterPriceTwoCheckBox.setText("400 EGP - 1000 EGP");
            filterPriceThreeCheckBox.setText("+ 1000 EGP");
        } else if (category == "beautyCare" && sub_category == "makeUp") {

            brands.add("All brands");
            brands.add("Bioderma");
            brands.add("Maybelline New York");

            filterPriceOneCheckBox.setText("Less than 100 EGP");
            filterPriceTwoCheckBox.setText("100 EGP - 300 EGP");
            filterPriceThreeCheckBox.setText("+ 300 EGP");
        } else if (category == "beautyCare" && sub_category == "skinCare") {

            brands.add("All brands");
            brands.add("L'oreal Paris");

            filterPriceOneCheckBox.setText("Less than 100 EGP");
            filterPriceTwoCheckBox.setText("100 EGP - 300 EGP");
            filterPriceThreeCheckBox.setText("+ 300 EGP");
        } else if ((category == "homeAppliances" && sub_category == "blendersAndMixers") ||
                (category == "homeAppliances" && sub_category == "microwaves")) {

            brands.add("All brands");
            brands.add("Tornado");
            brands.add("Braun");

            filterPriceOneCheckBox.setText("Less than 1000 EGP");
            filterPriceTwoCheckBox.setText("1000 EGP - 5000 EGP");
            filterPriceThreeCheckBox.setText("+ 5000 EGP");
        } else if ((category == "laptopAndPC" && sub_category == "laptops")) {

            brands.add("All brands");
            brands.add("Lenovo");

            filterPriceOneCheckBox.setText("Less than 10000 EGP");
            filterPriceTwoCheckBox.setText("10000 EGP - 15000 EGP");
            filterPriceThreeCheckBox.setText("+ 15000 EGP");
        } else if ((category == "laptopAndPC" && sub_category == "laptopBags")) {

            brands.add("All brands");
            brands.add("Lenovo");

            filterPriceOneCheckBox.setText("Less than 400 EGP");
            filterPriceTwoCheckBox.setText("+ 400 EGP");
            filterPriceThreeCheckBox.setVisibility(View.GONE);
        } else if ((category == "mobilesAndTablets" && sub_category == "mobiles")) {

            brands.add("All brands");
            brands.add("Xiaomi");

            filterPriceOneCheckBox.setText("Less than 3000 EGP");
            filterPriceTwoCheckBox.setText("3000 EGP - 5000 EGP");
            filterPriceThreeCheckBox.setText("+ 5000 EGP");
        } else if ((category == "mobilesAndTablets" && sub_category == "tablets")) {

            brands.add("All brands");
            brands.add("Lenovo");
            brands.add("Samsung");

            filterPriceOneCheckBox.setText("Less than 3000 EGP");
            filterPriceTwoCheckBox.setText("3000 EGP - 5000 EGP");
            filterPriceThreeCheckBox.setText("+ 5000 EGP");
        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            filterListener = (BottomSheetFilterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implemented BottomSheetListener");
        }
    }

    @Override
    public void sendTextFilter(String filterValue) {
       filterValuesHashSet.add(filterValue);

       Log.i("TAG", "sendTextFilter: "+filterValuesHashSet);
    }
}
