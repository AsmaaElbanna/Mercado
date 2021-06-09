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
import com.iti.mercado.model.Laptop;
import com.iti.mercado.utilities.DatabaseCart;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemLaptopActivity extends AppCompatActivity {

    Button addCartButton;
    private String category,sub_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item_laptop);

        TextView storage, ram, processor, operating_system, model, graphics_card_type, display_size, color, brand, price;

        ImageSlider imageSlider = findViewById(R.id.slider);
        price = findViewById(R.id.price_value);
        brand = findViewById(R.id.brand_value);
        color = findViewById(R.id.color_value);
        display_size = findViewById(R.id.display_size_value);
        graphics_card_type = findViewById(R.id.graphics_card_type_value);
        model = findViewById(R.id.model_value);
        operating_system = findViewById(R.id.operating_system_value);
        processor = findViewById(R.id.processor_value);
        ram = findViewById(R.id.ram_value);
        storage = findViewById(R.id.storage_value);
        addCartButton=findViewById(R.id.add);

        Laptop laptop = (Laptop) getIntent().getSerializableExtra("MyClass");

        //slider part
        List<SlideModel> slideModels=new ArrayList<>();
        for (String slider:laptop.getSlider_images()){
            slideModels.add(new SlideModel(slider, laptop.getItem_title()));
        }
        imageSlider.setImageList(slideModels,true);

        price.setText(laptop.getItem_price()+ " LE");
        brand.setText(laptop.getBrand());
        color.setText(laptop.getColor());
        display_size.setText(laptop.getDisplay_size());
        graphics_card_type.setText(laptop.getGraphics_card_type());
        model.setText(laptop.getModel());
        operating_system.setText(laptop.getOperating_system());
        processor.setText(laptop.getProcessor());
        ram.setText(laptop.getRam());
        storage.setText(laptop.getStorage());

        // cart part
        category = getIntent().getStringExtra("category");
        sub_category= getIntent().getStringExtra("subcategory");
        Cart cart =new Cart();
        cart.setItemId(laptop.getItem_id());
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