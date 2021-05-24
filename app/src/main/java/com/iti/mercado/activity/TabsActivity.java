package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

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
        
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab().setText("Shop").setIcon(R.drawable.ic_baseline_shop_24));
        tabLayout.addTab(tabLayout.newTab().setText("Categories").setIcon(R.drawable.ic_baseline_category_24));
        tabLayout.addTab(tabLayout.newTab().setText("Cart").setIcon(R.drawable.ic_baseline_add_shopping_cart_24));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite").setIcon(R.drawable.ic_baseline_favorite_24));
        tabLayout.addTab(tabLayout.newTab().setText("Account").setIcon(R.drawable.ic_baseline_account_circle_24));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}