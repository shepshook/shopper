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

import com.shepshook.shopper.adapter.CategoriesAdapter;
import com.shepshook.shopper.adapter.RecentProductsAdapter;
import com.shepshook.shopper.databinding.FragmentCategoriesBinding;
import com.shepshook.shopper.viewmodel.CategoriesViewModel;

import static androidx.recyclerview.widget.RecyclerView.*;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;
    private CategoriesViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
//        viewModel.reloadDataFromApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        binding.recentProductsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), HORIZONTAL, false));

        // Create new adapter if data changes and update view with it
        viewModel.categories.observe(
                getViewLifecycleOwner(),
                x -> binding.categoriesRecyclerView.setAdapter(new CategoriesAdapter(x)));

        viewModel.recentProducts.observe(getViewLifecycleOwner(), x -> {
            binding.recentProductsRecyclerView.setVisibility(x.isEmpty() ? View.GONE : View.VISIBLE);
            binding.recentProductsRecyclerView.setAdapter(new RecentProductsAdapter(x));
        });
    }
}