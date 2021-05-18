package com.iti.mercado.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment =null;
        switch (position){
            case 0:
                fragment=new ShopFragment();
            case 1:
                fragment=new CategoriesFragment();
            case 2:
                fragment=new CartFragment();
            case 3:
                fragment=new FavoriteFragment();
            case 4:
                fragment=new AccountFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Shop";
            case 1:
                return "Explore";
            case 2:
                return "Cart";
            case 3:
                return "Favorite";
            case 4:
                return "Account";
        }
       return null;
    }
}
