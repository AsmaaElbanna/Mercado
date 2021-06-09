package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.iti.mercado.R;
import com.iti.mercado.model.Cart;
import com.iti.mercado.model.LaptopBag;
import com.iti.mercado.utilities.DatabaseCart;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemLaptopBagActivity extends AppCompatActivity {

    Button addCartButton;
    private String category,sub_category;

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
        addCartButton=findViewById(R.id.add);


        LaptopBag laptopBag = (LaptopBag) getIntent().getSerializableExtra("MyClass");

        //slider part
        List<SlideModel> slideModels=new ArrayList<>();
        for (String slider:laptopBag.getSlider_images()){
            slideModels.add(new SlideModel(slider, laptopBag.getItem_title()));
        }
        imageSlider.setImageList(slideModels,true);

        price.setText(laptopBag.getItem_price()+ " LE");
        brand.setText(laptopBag.getBrand());
        color.setText(laptopBag.getColor());
        compatible_with.setText(laptopBag.getCompatible_with());
        warranty_years.setText(laptopBag.getWarranty_years());
        model.setText(laptopBag.getModel());
        water_resistant.setText(laptopBag.getWater_resistant());

        // cart part
        category = getIntent().getStringExtra("category");
        sub_category= getIntent().getStringExtra("subcategory");
        Cart cart =new Cart();
        cart.setItemId(laptopBag.getItem_id());
        cart.setCategory(category);
        Log.i("TAG", "onCreate: category "+category);
        cart.setSubCategory(sub_category);
        Log.i("TAG", "onCreate: category "+sub_category);
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
            }else{
                databaseCart.write(cart
                        , () -> {
                            addCartButton.setText("Added");
                        });
            }
        });

    }
}