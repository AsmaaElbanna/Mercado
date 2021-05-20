package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iti.mercado.R;
import com.iti.mercado.model.KidsClothing;
import com.iti.mercado.model.KidsShoes;
import com.iti.mercado.model.WomenClothing;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemClothingActivity extends AppCompatActivity {
    TextView price,brand,size,color,quality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item_clothing);

        //slider part
        ImageSlider imageSlider = findViewById(R.id.slider);



        if(getIntent().getSerializableExtra("MyClass") instanceof KidsClothing || getIntent().getSerializableExtra("MyClass") instanceof WomenClothing){
            KidsClothing  girlsAndBoysClothing = (KidsClothing) getIntent().getSerializableExtra("MyClass");
            List<SlideModel> slideModels=new ArrayList<>();
            //String [] url =girlsAndBoysClothing.getSlider_images();

//            for(int i =0 ;i<url.length;i++){
//                slideModels.add(new SlideModel(url[i],girlsAndBoysClothing.getItem_title()));
//                imageSlider.setImageList(slideModels,true);
//            }





        }else if(getIntent().getSerializableExtra("MyClass") instanceof KidsShoes){

            KidsShoes  kiro = (KidsShoes) getIntent().getSerializableExtra("MyClass");
            Log.i("Kiro", "onCreate: KidsShoes"+kiro.getSize());
        }

    }
}