package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iti.mercado.R;
import com.iti.mercado.model.Cart;
import com.iti.mercado.model.HomeAppliance;
import com.iti.mercado.utilities.DatabaseCart;


import java.util.ArrayList;
import java.util.List;

public class DetailsItemHomeApplianceActivity extends AppCompatActivity {

    private Button addCartButton;
    private String category, sub_category;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item_home_appliance);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView power, model_number, item_type, item_capacity, item_brand, item_Features, country, color, price;

        ImageSlider imageSlider = findViewById(R.id.slider);
        price = findViewById(R.id.price_value);
        color = findViewById(R.id.color_value);
        country = findViewById(R.id.country_value);
        item_Features = findViewById(R.id.item_Features_value);
        item_brand = findViewById(R.id.item_brand_value);
        item_capacity = findViewById(R.id.item_capacity_value);
        item_type = findViewById(R.id.item_type_value);
        model_number = findViewById(R.id.model_value);
        power = findViewById(R.id.power_value);
        addCartButton = findViewById(R.id.add);

        backArrow = findViewById(R.id.back_button);
        backArrow.setOnClickListener(v -> {
            finish();
        });

        HomeAppliance homeAppliance = (HomeAppliance) getIntent().getSerializableExtra("MyClass");


        //slider part
        List<SlideModel> slideModels = new ArrayList<>();
        for (String slider : homeAppliance.getSlider_images()) {
            slideModels.add(new SlideModel(slider, homeAppliance.getItem_title()));
        }
        imageSlider.setImageList(slideModels, true);

        price.setText(homeAppliance.getItem_price() + " EGP");
        item_brand.setText(homeAppliance.getBrand());
        color.setText(homeAppliance.getColor());
        country.setText(homeAppliance.getCountry());
        item_Features.setText(homeAppliance.getItem_Features());
        item_capacity.setText(homeAppliance.getItem_capacity());
        item_type.setText(homeAppliance.getItem_type());
        model_number.setText(homeAppliance.getModel_number());
        power.setText(homeAppliance.getPower());
        // cart part
        category = getIntent().getStringExtra("category");
        sub_category = getIntent().getStringExtra("subcategory");
        Cart cart = new Cart();
        cart.setItemId(homeAppliance.getItem_id());
        cart.setCategory(category);
        Log.i("TAG", "onCreate: category " + category);
        cart.setSubCategory(sub_category);
        Log.i("TAG", "onCreate: category " + sub_category);
        cart.setCount(1);
        DatabaseCart databaseCart = new DatabaseCart();

        databaseCart.read(cart, flag -> {
            if (flag) {
                addCartButton.setText("Added");
            }
        });
        addCartButton.setOnClickListener(v -> {

            if (addCartButton.getText() == "Added") {
                databaseCart.delete(cart, () -> {
                    addCartButton.setText("Add to cart");
                });
            } else {
                databaseCart.write(cart
                        , () -> {
                            addCartButton.setText("Added");
                        });
            }
        });

    }
}