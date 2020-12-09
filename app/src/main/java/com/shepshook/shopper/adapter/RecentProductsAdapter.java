package com.shepshook.shopper.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shepshook.shopper.data.entity.Product;
import com.shepshook.shopper.databinding.ItemRecentProductBinding;

import java.util.List;

public class RecentProductsAdapter extends RecyclerView.Adapter<RecentProductsAdapter.ViewHolder> {

    private final List<Product> list;

    public RecentProductsAdapter(List<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecentProductBinding binding = ItemRecentProductBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecentProductBinding binding;

        public ViewHolder(ItemRecentProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected void bind(Product item) {
            if (item.imageUrl != null)
                Glide.with(binding.getRoot()).load(item.imageUrl).into(binding.recentProductImage);
            binding.recentProductImage.setContentDescription(item.name);
        }
    }
}
