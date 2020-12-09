package com.shepshook.shopper.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shepshook.shopper.data.entity.Product;
import com.shepshook.shopper.databinding.ItemProductBinding;
import com.shepshook.shopper.fragment.ProductsFragmentDirections;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private final List<Product> list;

    public ProductsAdapter(List<Product> list) { this.list = list; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemProductBinding binding = ItemProductBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() { return list.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;

        public ViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected void bind(Product item) {
            itemView.setOnClickListener((view) -> {
                NavDirections action = ProductsFragmentDirections.actionProductsFragmentToProductInfoPagerFragment(item.id);
                Navigation.findNavController(view).navigate(action);
            });

            binding.productName.setText(item.name);
            binding.productPrice.setText(String.valueOf(item.price));
            binding.productImage.setContentDescription(item.name);

            if (item.imageUrl != null)
                Glide.with(binding.getRoot()).load(item.imageUrl).into(binding.productImage);
        }
    }
}
