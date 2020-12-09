package com.shepshook.shopper.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shepshook.shopper.data.entity.Product;
import com.shepshook.shopper.databinding.FragmentProductInfoBinding;

import java.util.function.Consumer;

public class ProductInfoFragment extends Fragment {
    private Product model;
    private FragmentProductInfoBinding binding;
    private Consumer<Product> listener;
    private float mScaleFactor = 1.0f;

    public ProductInfoFragment(Product model, Consumer<Product> listener){
        this.model = model;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.infoProductName.setText(model.name);
        binding.infoProductDescription.setText(model.description);
        binding.infoProductPrice.setText("$" + model.price);
        binding.infoProductImage.setContentDescription(model.name);
        binding.infoProductBuy.setOnClickListener(x -> listener.accept(model));

        if (model.imageUrl != null) {
            Glide.with(this).load(model.imageUrl).into(binding.infoProductImage);

            ScaleGestureDetector scaleGestureDetector =
                    new ScaleGestureDetector(requireActivity(), new ScaleListener(binding.infoProductImage));
            binding.infoProductImage.setOnTouchListener((v, event) -> scaleGestureDetector.onTouchEvent(event));
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private final View view;

        private ScaleListener(View view) {
            this.view = view;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 2.0f));

            view.setScaleX(mScaleFactor);
            view.setScaleY(mScaleFactor);

            return true;
        }
    }
}