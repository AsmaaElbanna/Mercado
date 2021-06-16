package com.iti.mercado.utilities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

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
    private RadioButton filterPriceOneRadioButton, filterPriceTwoRadioButton, filterPriceThreeRadioButton;
    private Button applyFilterButton;
    private BottomSheetFilterListener filterListener;
    private String category, sub_category;
    private HashSet<String> filterValuesBrandsHashSet;
    private int lessThanPrice, greaterThanPrice, rangePrice1, rangePrice2;
    private List<Double> filterValuesPrice;
    private int flagCheckedRadioButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        brands = new ArrayList<>();
        filterValuesBrandsHashSet = new HashSet<>();
        filterValuesPrice = new ArrayList<>();
        flagCheckedRadioButton =-1;
        category = getArguments().getString("Category ItemListActivity");
        sub_category = getArguments().getString("SubCategory ItemListActivity");

        View view = inflater.inflate(R.layout.bottom_sheet_filter, container, false);
        recyclerView = view.findViewById(R.id.list_brands);
        filterPriceOneRadioButton = view.findViewById(R.id.filter_price_one);
        filterPriceTwoRadioButton = view.findViewById(R.id.filter_price_two);
        filterPriceThreeRadioButton = view.findViewById(R.id.filter_price_three);
        applyFilterButton = view.findViewById(R.id.apply_filter);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BrandAdapter brandAdapter = new BrandAdapter(getActivity(), brands, this);
        recyclerView.setAdapter(brandAdapter);
        setFilterValues();

        // radio buttons action
        filterPriceOneRadioButton.setOnClickListener(v -> {
            filterValuesPrice.clear();
            filterValuesPrice.add((double) lessThanPrice);
            flagCheckedRadioButton =0;
        });

        filterPriceTwoRadioButton.setOnClickListener(v -> {
            filterValuesPrice.clear();
            filterValuesPrice.add((double) rangePrice1);
            filterValuesPrice.add((double) rangePrice2);
            flagCheckedRadioButton =1;
        });

        filterPriceThreeRadioButton.setOnClickListener(v -> {
            filterValuesPrice.clear();
            filterValuesPrice.add((double) greaterThanPrice);
            flagCheckedRadioButton =2;

        });
        // apply filter button action
        applyFilterButton.setOnClickListener(v -> {
            filterListener.onApplyFilterClicked(filterValuesBrandsHashSet, filterValuesPrice, flagCheckedRadioButton);
            Log.i("TAG", "onCreateView: filterValuesPrice"+ filterValuesPrice);
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
            brands.add("Zanzea");
            brands.add("SHEIN");
            brands.add("Defacto");

            lessThanPrice = 300;
            greaterThanPrice = 600;
            rangePrice1 = 300;
            rangePrice2 = 600;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if (category == "Women's Fashion" && sub_category == "bags") {

            brands.add("All brands");
            brands.add("Shein");
            brands.add("Generic");

            lessThanPrice = 400;
            greaterThanPrice = 700;
            rangePrice1 = 400;
            rangePrice2 = 700;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if ((category == "Girl's Fashion" && sub_category == "clothing") ||
                (category == "Girl's Fashion" && sub_category == "shoes") ||
                (category == "boy's fashion" && sub_category == "clothing") ||
                (category == "boy's fashion" && sub_category == "shoes")) {

            brands.add("All brands");
            brands.add("Zara");
            brands.add("Defacto");

            lessThanPrice = 400;
            greaterThanPrice = 600;
            rangePrice1 = 400;
            rangePrice2 = 600;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");

        } else if (category == "personalCare" && sub_category == "beautyEquipment") {

            brands.add("All brands");
            brands.add("Hapilin");

            lessThanPrice = 400;
            greaterThanPrice = 1000;
            rangePrice1 = 400;
            rangePrice2 = 1000;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if (category == "personalCare" && sub_category == "hairStylers") {

            brands.add("All brands");
            brands.add("Mienta");

            lessThanPrice = 400;
            greaterThanPrice = 1000;
            rangePrice1 = 400;
            rangePrice2 = 1000;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if (category == "beautyCare" && sub_category == "makeUp") {

            brands.add("All brands");
            brands.add("Bioderma");
            brands.add("Maybelline New York");

            lessThanPrice = 100;
            greaterThanPrice = 200;
            rangePrice1 = 100;
            rangePrice2 = 200;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if (category == "beautyCare" && sub_category == "skinCare") {

            brands.add("All brands");
            brands.add("L'oreal Paris");

            lessThanPrice = 100;
            greaterThanPrice = 300;
            rangePrice1 = 100;
            rangePrice2 = 300;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if ((category == "homeAppliances" && sub_category == "blendersAndMixers") ||
                (category == "homeAppliances" && sub_category == "microwaves")) {

            brands.add("All brands");
            brands.add("Tornado");
            brands.add("Braun");

            lessThanPrice = 1000;
            greaterThanPrice = 5000;
            rangePrice1 = 1000;
            rangePrice2 = 5000;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if ((category == "laptopAndPC" && sub_category == "laptops")) {

            brands.add("All brands");
            brands.add("Lenovo");

            lessThanPrice = 10000;
            greaterThanPrice = 15000;
            rangePrice1 = 10000;
            rangePrice2 = 15000;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if ((category == "laptopAndPC" && sub_category == "laptopBags")) {

            brands.add("All brands");
            brands.add("Lenovo");

            lessThanPrice = 350;
            greaterThanPrice = 500;
            rangePrice1 = 300;
            rangePrice2 = 500;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if ((category == "mobilesAndTablets" && sub_category == "mobiles")) {

            brands.add("All brands");
            brands.add("Xiaomi");

            lessThanPrice = 3000;
            greaterThanPrice = 5000;
            rangePrice1 = 3000;
            rangePrice2 = 5000;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
        } else if ((category == "mobilesAndTablets" && sub_category == "tablets")) {

            brands.add("All brands");
            brands.add("Lenovo");
            brands.add("Samsung");

            lessThanPrice = 3000;
            greaterThanPrice = 5000;
            rangePrice1 = 3000;
            rangePrice2 = 5000;
            filterPriceOneRadioButton.setText("Less than " + lessThanPrice + " EGP");
            filterPriceTwoRadioButton.setText(rangePrice1 + " EGP - " + rangePrice2 + " EGP");
            filterPriceThreeRadioButton.setText("+ " + greaterThanPrice + " EGP");
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
        filterValuesBrandsHashSet.add(filterValue);

//       Log.i("TAG", "sendTextFilter: "+filterValuesHashSet);
    }
}
