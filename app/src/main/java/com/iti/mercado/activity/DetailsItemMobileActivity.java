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
import com.iti.mercado.model.Mobile;
import com.iti.mercado.utilities.DatabaseCart;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemMobileActivity extends AppCompatActivity {

    Button addCartButton;
    private String category,sub_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item_mobile);

        TextView battery_capacity, rear_camera, processor, memory, front_camera, model, display, connectivity, color, brand, price;

        ImageSlider imageSlider = findViewById(R.id.slider);

        price = findViewById(R.id.price_value);
        brand = findViewById(R.id.brand_value);
        color = findViewById(R.id.color_value);
        display = findViewById(R.id.display_value);
        battery_capacity = findViewById(R.id.battery_capacity_value);
        model = findViewById(R.id.model_value);
        rear_camera = findViewById(R.id.rear_camera_value);
        processor = findViewById(R.id.processor_value);
        memory = findViewById(R.id.memory_value);
        front_camera = findViewById(R.id.front_camera_value);
        connectivity = findViewById(R.id.connectivity_value);
        addCartButton=findViewById(R.id.add);

        Mobile mobile = (Mobile) getIntent().getSerializableExtra("MyClass");

        //slider part
        List<SlideModel> slideModels = new ArrayList<>();
        for (String slider:mobile.getSlider_images()){
            slideModels.add(new SlideModel(slider, mobile.getItem_title()));
        }
        imageSlider.setImageList(slideModels, true);

        price.setText(mobile.getItem_price() + " LE");
        brand.setText(mobile.getBrand());
        color.setText(mobile.getColor());
        display.setText(mobile.getDisplay());
        battery_capacity.setText(mobile.getBattery_capacity());
        model.setText(mobile.getModel());
        rear_camera.setText(mobile.getRear_camera());
        processor.setText(mobile.getProcessor());
        memory.setText(mobile.getMemory());
        front_camera.setText(mobile.getFront_camera());
        connectivity.setText(mobile.getConnectivity());

        // cart part
        category = getIntent().getStringExtra("category");
        sub_category= getIntent().getStringExtra("subcategory");
        Cart cart =new Cart();
        cart.setItemId(mobile.getItem_id());
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