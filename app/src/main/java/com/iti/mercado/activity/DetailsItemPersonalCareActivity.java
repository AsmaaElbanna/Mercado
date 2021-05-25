package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iti.mercado.R;
import com.iti.mercado.model.HomeAppliance;
import com.iti.mercado.model.PersonalCare;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemPersonalCareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item_personal_care);
        TextView model_number, item_type, item_brand, item_Features, color, price;

        ImageSlider imageSlider = findViewById(R.id.slider);
        price = findViewById(R.id.price_value);
        color = findViewById(R.id.color_value);
        item_Features = findViewById(R.id.item_Features_value);
        item_brand = findViewById(R.id.item_brand_value);
        item_type = findViewById(R.id.item_type_value);
        model_number = findViewById(R.id.model_value);

        PersonalCare personalCare = (PersonalCare) getIntent().getSerializableExtra("MyClass");

        //slider part
        List<SlideModel> slideModels=new ArrayList<>();
        for (String slider:personalCare.getSlider_images()){
            slideModels.add(new SlideModel(slider, personalCare.getItem_title()));
        }
        imageSlider.setImageList(slideModels,true);

        price.setText(personalCare.getItem_price()+ " LE");
        item_brand.setText(personalCare.getItem_brand());
        color.setText(personalCare.getColor());
        item_Features.setText(personalCare.getItem_features());
        item_type.setText(personalCare.getItem_type());
        model_number.setText(personalCare.getModel_number());

    }
}
