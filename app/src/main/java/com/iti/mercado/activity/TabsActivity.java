package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.iti.mercado.R;
import com.iti.mercado.adapter.TabsAdapter;

public class TabsActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        int onTabSelectedColor = ContextCompat.getColor(TabsActivity.this, R.color.accent);
        int onTabUnselectedColor = ContextCompat.getColor(TabsActivity.this, R.color.divider);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab().setText("Shop").setIcon(R.drawable.ic_baseline_shop_24));
        tabLayout.addTab(tabLayout.newTab().setText("Explore").setIcon(R.drawable.ic_baseline_category_24));
        tabLayout.addTab(tabLayout.newTab().setText("Cart").setIcon(R.drawable.ic_baseline_add_shopping_cart_24));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite").setIcon(R.drawable.ic_baseline_favorite_24));
        tabLayout.addTab(tabLayout.newTab().setText("Account").setIcon(R.drawable.ic_baseline_account_circle_24));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        //viewPager.setCurrentItem(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(onTabSelectedColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(onTabUnselectedColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}