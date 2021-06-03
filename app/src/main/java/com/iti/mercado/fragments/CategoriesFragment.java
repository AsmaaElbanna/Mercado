package com.iti.mercado.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.iti.mercado.R;
import com.iti.mercado.adapter.CategoriesAdapter;
import com.iti.mercado.model.Category;
import com.iti.mercado.utilities.Network;
import com.iti.mercado.utilities.OnResponseRetrofit;

import java.util.List;

import retrofit2.Call;

public class CategoriesFragment extends Fragment implements OnResponseRetrofit<Category> {

    private RecyclerView recyclerView;
    private LinearLayout subCategoriesLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        subCategoriesLinearLayout = view.findViewById(R.id.subCategoriesLinearLayout);
        recyclerView = view.findViewById(R.id.categoriesRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Call<List<Category>> call = Network.getJsonQ().getCategories();
        Network.parsJson(call, this);

        return view;
    }

    @Override
    public void onResponse(List<Category> items) {

        CategoriesAdapter adapter = new CategoriesAdapter(getActivity(), items,
                subCategoriesLinearLayout);
        recyclerView.setAdapter(adapter);

    }
}