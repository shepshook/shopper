package com.shepshook.shopper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shepshook.shopper.data.entity.CartItemExp;
import com.shepshook.shopper.databinding.ItemCartBinding;

import java.util.List;
import java.util.function.Consumer;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private final List<CartItemExp> list;

    private Consumer<CartItemExp> minusListener;
    private Consumer<CartItemExp> plusListener;
    private Consumer<CartItemExp> deleteListener;

    public CartItemsAdapter(List<CartItemExp> list,
                            Consumer<CartItemExp> minusListener,
                            Consumer<CartItemExp> plusListener,
                            Consumer<CartItemExp> deleteListener) {
        this.minusListener = minusListener;
        this.plusListener = plusListener;
        this.deleteListener = deleteListener;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCartBinding binding = ItemCartBinding.inflate(inflater, parent, false);

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
        private final ItemCartBinding binding;

        public ViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected void bind(CartItemExp item) {
            binding.cartProductName.setText(item.product.name);
            binding.cartProductPrice.setText("$" + item.product.price + " / 1");
            binding.cartProductTotalPrice.setText("$" + item.getTotalPrice());
            binding.cartProductQuantity.setText(String.valueOf(item.cartItem.quantity));

            if (minusListener != null)
                binding.cartButtonMinus.setOnClickListener(view -> minusListener.accept(item));
            if (plusListener != null)
                binding.cartButtonPlus.setOnClickListener(view -> plusListener.accept(item));
            if (deleteListener != null)
                binding.cartButtonDelete.setOnClickListener(view -> deleteListener.accept(item));

            if (item.cartItem.quantity == 1) {
                binding.cartButtonMinus.setVisibility(View.INVISIBLE);
                binding.cartButtonDelete.setVisibility(View.VISIBLE);
            }
            else {
                binding.cartButtonDelete.setVisibility(View.GONE);
                binding.cartButtonMinus.setVisibility(View.VISIBLE);
            }

            if (item.product.imageUrl != null)
                Glide.with(binding.getRoot()).load(item.product.imageUrl).into(binding.cartProductImage);
        }
    }
}
