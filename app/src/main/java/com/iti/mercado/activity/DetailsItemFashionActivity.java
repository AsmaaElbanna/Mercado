package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iti.mercado.R;
import com.iti.mercado.model.KidsClothing;
import com.iti.mercado.model.KidsShoes;
import com.iti.mercado.model.MakeUp;
import com.iti.mercado.model.SkinCare;
import com.iti.mercado.model.WomenBags;
import com.iti.mercado.model.WomenClothing;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemFashionActivity extends AppCompatActivity {
    TextView price, brand, size, color, quality;
    TextView sizeText,qualityText,colorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item_fashion);
        price = findViewById(R.id.price_value);
        brand = findViewById(R.id.brand_value);
        size = findViewById(R.id.size_value);
        color = findViewById(R.id.color_value);
        quality = findViewById(R.id.quality_value);
        sizeText =findViewById(R.id.size_txt);
        qualityText=findViewById(R.id.quality_txt);
        colorText=findViewById(R.id.color_txt);

        //slider part
        ImageSlider imageSlider = findViewById(R.id.slider);


        if (getIntent().getSerializableExtra("MyClass") instanceof KidsClothing) {
            KidsClothing girlsAndBoysClothing = (KidsClothing) getIntent().getSerializableExtra("MyClass");

            List<SlideModel> slideModels = new ArrayList<>();

            for(String slider:girlsAndBoysClothing.getSlider_image()){
                slideModels.add(new SlideModel(slider, girlsAndBoysClothing.getItem_title()));
                imageSlider.setImageList(slideModels, true);
            }
//
            price.setText(girlsAndBoysClothing.getItem_price());
            brand.setText(girlsAndBoysClothing.getBrand());
            color.setText(girlsAndBoysClothing.getColor());
            size.setText(girlsAndBoysClothing.getSize());
            quality.setText(girlsAndBoysClothing.getQuality());

        } else if (getIntent().getSerializableExtra("MyClass") instanceof WomenClothing) {
            WomenClothing womenClothing = (WomenClothing) getIntent().getSerializableExtra("MyClass");
            List<SlideModel> slideModels = new ArrayList<>();
            for(String slider:womenClothing.getSlider_images()){
                slideModels.add(new SlideModel(slider, womenClothing.getItem_title()));
                imageSlider.setImageList(slideModels, true);
            }
            price.setText(womenClothing.getItem_price());
            brand.setText(womenClothing.getBrand());
            color.setText(womenClothing.getColor());
            size.setText(womenClothing.getLength());
            quality.setText(womenClothing.getMaterial());
        } else if (getIntent().getSerializableExtra("MyClass") instanceof KidsShoes) {
            KidsShoes kidsShoes = (KidsShoes) getIntent().getSerializableExtra("MyClass");
            List<SlideModel> slideModels = new ArrayList<>();
            for(String slider:kidsShoes.getSlider_image()){
                slideModels.add(new SlideModel(slider, kidsShoes.getItem_title()));
                imageSlider.setImageList(slideModels, true);
            }

            price.setText(kidsShoes.getItem_price());
            brand.setText(kidsShoes.getBrand());
            color.setText(kidsShoes.getColor());
            size.setText(kidsShoes.getSize());
            quality.setText(kidsShoes.getMaterial());

        }else if (getIntent().getSerializableExtra("MyClass") instanceof WomenBags) {
            WomenBags womenBags = (WomenBags) getIntent().getSerializableExtra("MyClass");
            List<SlideModel> slideModels = new ArrayList<>();
            for(String slider:womenBags.getSlider_images()){
                slideModels.add(new SlideModel(slider, womenBags.getItem_title()));
                imageSlider.setImageList(slideModels, true);
            }

            price.setText(womenBags.getItem_price());
            brand.setText(womenBags.getBrand());
            color.setText(womenBags.getColor());
            quality.setText(womenBags.getMaterial());
            sizeText.setVisibility(View.GONE);

        } else if(getIntent().getSerializableExtra("MyClass") instanceof MakeUp) {
            MakeUp makeUp = (MakeUp) getIntent().getSerializableExtra("MyClass");
            List<SlideModel> slideModels = new ArrayList<>();
            for(String slider:makeUp.getSlider_images()){
                slideModels.add(new SlideModel(slider, makeUp.getItem_title()));
                imageSlider.setImageList(slideModels, true);
            }

            price.setText(makeUp.getItem_price());
            brand.setText(makeUp.getBrand());
            color.setText(makeUp.getColor());
            qualityText.setText("Description");
            quality.setText(makeUp.getItem_description());
            sizeText.setText("Weight");
            size.setText(makeUp.getWeight());
        }else if(getIntent().getSerializableExtra("MyClass") instanceof SkinCare) {
            SkinCare skinCare = (SkinCare) getIntent().getSerializableExtra("MyClass");
            List<SlideModel> slideModels = new ArrayList<>();
            for(String slider:skinCare.getSlider_images()){
                slideModels.add(new SlideModel(slider, skinCare.getItem_title()));
                imageSlider.setImageList(slideModels, true);
            }
            price.setText(skinCare.getItem_price());
            brand.setText(skinCare.getBrand());
            colorText.setText("Recommended use");
            color.setText(skinCare.getRecommended_use());
            qualityText.setText("Description");
            quality.setText(skinCare.getItem_description());
            size.setText(skinCare.getSize());
        }
    }
}