package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iti.mercado.R;
import com.iti.mercado.model.LaptopBag;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemLaptopBagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item_laptop_bag);

        TextView water_resistant, warranty_years, compatible_with, model, color, brand, price;

        ImageSlider imageSlider = findViewById(R.id.slider);
        price = findViewById(R.id.price_value);
        brand = findViewById(R.id.brand_value);
        color = findViewById(R.id.color_value);
        compatible_with = findViewById(R.id.compatible_with_value);
        warranty_years = findViewById(R.id.warranty_years_value);
        model = findViewById(R.id.model_value);
        water_resistant = findViewById(R.id.water_resistant_value);


        LaptopBag laptopBag = (LaptopBag) getIntent().getSerializableExtra("MyClass");

        //slider part
        List<SlideModel> slideModels=new ArrayList<>();
        for (int i = 0 ; i < laptopBag.getSlider_images().length;i++){
            slideModels.add(new SlideModel(laptopBag.getSlider_images()[i],laptopBag.getItem_title()));
        }
        imageSlider.setImageList(slideModels,true);

        price.setText(laptopBag.getItem_price()+ " LE");
        brand.setText(laptopBag.getBrand());
        color.setText(laptopBag.getColor());
        compatible_with.setText(laptopBag.getCompatible_with());
        warranty_years.setText(laptopBag.getWarranty_years());
        model.setText(laptopBag.getModel());
        water_resistant.setText(laptopBag.getWater_resistant());

    }
}