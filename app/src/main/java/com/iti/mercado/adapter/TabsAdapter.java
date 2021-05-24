package com.iti.mercado.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.iti.mercado.fragments.AccountFragment;
import com.iti.mercado.fragments.CartFragment;
import com.iti.mercado.fragments.CategoriesFragment;
import com.iti.mercado.fragments.FavoriteFragment;
import com.iti.mercado.fragments.ShopFragment;

public class TabsAdapter extends FragmentPagerAdapter {

    int totalTabs;

    public TabsAdapter(FragmentManager fm, int totalTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ShopFragment shopFragment = new ShopFragment();
                return shopFragment;
            case 1:
                CategoriesFragment categoriesFragment = new CategoriesFragment();
                return categoriesFragment;
            case 2:
                CartFragment cartFragment = new CartFragment();
                return cartFragment;
            case 3:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                return favoriteFragment;
            default:
                AccountFragment accountFragment = new AccountFragment();
                return accountFragment;

        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

}
