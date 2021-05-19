package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.slider.Slider;
import com.iti.mercado.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemActivity extends AppCompatActivity {
    TextView price,brand,size,color,quality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item);

        //slider part
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel("https://eg.jumia.is/unsafe/fit-in/500x500/filters:fill(white)/product/20/088902/1.jpg?8656","Defacto Girl Purple Regular Fit Knitted Skirt"));
        slideModels.add(new SlideModel("https://eg.jumia.is/unsafe/fit-in/500x500/filters:fill(white)/product/74/699802/1.jpg?2278","Defacto Boy White Regular Fit Stand- Up Collar Long Sleeve Long Sleeve Shirt"));
        slideModels.add(new SlideModel("https://eg.jumia.is/unsafe/fit-in/500x500/filters:fill(white)/product/69/108371/1.jpg?5171","3"));
        imageSlider.setImageList(slideModels,true);

      //details part
        price = findViewById(R.id.price_value);
        brand = findViewById(R.id.brand_value);
        size = findViewById(R.id.size_value);
        color = findViewById(R.id.color_value);
        quality = findViewById(R.id.quality_value);

        price.setText("EGP 470");
        brand.setText("SHEIN");
        size.setText("Waist: 22.0 ");
        color.setText("Purple");
        quality.setText("100% Cotton");





    }
}