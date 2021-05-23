package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iti.mercado.R;
import com.iti.mercado.model.Laptop;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemLaptopActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_details_item_laptop);
//
//        TextView storage, ram, processor, operating_system, model, graphics_card_type, display_size, color, brand, price;
//
//        ImageSlider imageSlider = findViewById(R.id.slider);
//        price = findViewById(R.id.price_value);
//        brand = findViewById(R.id.brand_value);
//        color = findViewById(R.id.color_value);
//        display_size = findViewById(R.id.display_size_value);
//        graphics_card_type = findViewById(R.id.graphics_card_type_value);
//        model = findViewById(R.id.model_value);
//        operating_system = findViewById(R.id.operating_system_value);
//        processor = findViewById(R.id.processor_value);
//        ram = findViewById(R.id.ram_value);
//        storage = findViewById(R.id.storage_value);
//
//        Laptop laptop = (Laptop) getIntent().getSerializableExtra("MyClass");
//
//        //slider part
//        List<SlideModel> slideModels=new ArrayList<>();
//        for (int i = 0 ; i < laptop.getSlider_images().length;i++){
//            slideModels.add(new SlideModel(laptop.getSlider_images()[i],laptop.getItem_title()));
//        }
//        imageSlider.setImageList(slideModels,true);
//
//        price.setText(laptop.getItem_price()+ " LE");
//        brand.setText(laptop.getBrand());
//        color.setText(laptop.getColor());
//        display_size.setText(laptop.getDisplay_size());
//        graphics_card_type.setText(laptop.getGraphics_card_type());
//        model.setText(laptop.getModel());
//        operating_system.setText(laptop.getOperating_system());
//        processor.setText(laptop.getProcessor());
//        ram.setText(laptop.getRam());
//        storage.setText(laptop.getStorage());
//    }
}