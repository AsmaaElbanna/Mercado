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
import com.iti.mercado.model.HomeAppliance;
import com.iti.mercado.model.PersonalCare;
import com.iti.mercado.utilities.DatabaseCart;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemPersonalCareActivity extends AppCompatActivity {

    private Button addCartButton;
    private  String category, sub_category;

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
        addCartButton = findViewById(R.id.add);

        PersonalCare personalCare = (PersonalCare) getIntent().getSerializableExtra("MyClass");

        //slider part
        List<SlideModel> slideModels=new ArrayList<>();
        for (String slider:personalCare.getSlider_images()){
            slideModels.add(new SlideModel(slider, personalCare.getItem_title()));
        }
        imageSlider.setImageList(slideModels,true);

        price.setText(personalCare.getItem_price()+ " LE");
        item_brand.setText(personalCare.getBrand());
        color.setText(personalCare.getColor());
        item_Features.setText(personalCare.getItem_features());
        item_type.setText(personalCare.getItem_type());
        model_number.setText(personalCare.getModel_number());

        // cart part
        category = getIntent().getStringExtra("category");
        sub_category= getIntent().getStringExtra("subcategory");
        Cart cart =new Cart();
        cart.setItemId(personalCare.getItem_id());
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
