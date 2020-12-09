package com.shepshook.shopper.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shepshook.shopper.ProductListActivityArgs;
import com.shepshook.shopper.R;
import com.shepshook.shopper.adapter.ProductsAdapter;
import com.shepshook.shopper.viewmodel.ProductsViewModel;

public class ProductsFragment extends Fragment {

    private ProductsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int categoryID = ProductListActivityArgs.fromBundle(requireActivity().getIntent().getExtras()).getCategoryID();
        viewModel = new ViewModelProvider(requireActivity()).get(ProductsViewModel.class);
        viewModel.InitFromCategoryID(categoryID);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.productsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel.productsLiveData.observe(getViewLifecycleOwner(), x -> recyclerView.setAdapter(new ProductsAdapter(x)));
    }
}