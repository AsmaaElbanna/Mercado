package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iti.mercado.R;
import com.iti.mercado.model.HomeAppliance;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemHomeApplianceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item_home_appliance);

        TextView power, model_number, item_type, item_capacity, item_brand, item_Features, country, color, price;

        ImageSlider imageSlider = findViewById(R.id.slider);
        price = findViewById(R.id.price_value);
        color = findViewById(R.id.color_value);
        country = findViewById(R.id.display_size_value);
        item_Features = findViewById(R.id.graphics_card_type_value);
        item_brand = findViewById(R.id.model_value);
        item_capacity = findViewById(R.id.operating_system_value);
        item_type = findViewById(R.id.processor_value);
        model_number = findViewById(R.id.ram_value);
        power = findViewById(R.id.storage_value);

        HomeAppliance homeAppliance = (HomeAppliance) getIntent().getSerializableExtra("MyClass");

        //slider part
        List<SlideModel> slideModels=new ArrayList<>();
        for (int i = 0; i < homeAppliance.getSlider_images().length; i++){
            slideModels.add(new SlideModel(homeAppliance.getSlider_images()[i], homeAppliance.getItem_title()));
        }
        imageSlider.setImageList(slideModels,true);

        price.setText(homeAppliance.getItem_price()+ " LE");
        item_brand.setText(homeAppliance.getItem_brand());
        color.setText(homeAppliance.getColor());
        country.setText(homeAppliance.getCountry());
        item_Features.setText(homeAppliance.getItem_Features());
        item_capacity.setText(homeAppliance.getItem_capacity());
        item_type.setText(homeAppliance.getItem_type());
        model_number.setText(homeAppliance.getModel_number());
        power.setText(homeAppliance.getPower());

    }
}