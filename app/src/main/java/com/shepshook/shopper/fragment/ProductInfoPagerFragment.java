package com.shepshook.shopper.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.shepshook.shopper.R;
import com.shepshook.shopper.data.entity.Product;
import com.shepshook.shopper.viewmodel.ProductsViewModel;

import java.util.List;

public class ProductInfoPagerFragment extends Fragment {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private ProductsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int productID = ProductInfoPagerFragmentArgs.fromBundle(getArguments()).getProductID();

        viewModel = new ViewModelProvider(requireActivity()).get(ProductsViewModel.class);
        viewModel.InitFromProductID(productID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_info_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.productInfoPagerView);

        List<Product> list = viewModel.products;
        pagerAdapter = new ScreenSlidePagerAdapter(getActivity(), list);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(list.indexOf(viewModel.startProduct), false);
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private List<Product> list;

        public ScreenSlidePagerAdapter(FragmentActivity fa, List<Product> list) {
            super(fa);
            this.list = list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Product item = list.get(position);
            viewModel.addRecent(item);
            return new ProductInfoFragment(list.get(position), x -> viewModel.addToCart(x));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}