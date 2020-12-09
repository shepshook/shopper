package com.shepshook.shopper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.shepshook.shopper.adapter.CartItemsAdapter;
import com.shepshook.shopper.data.entity.CartItemExp;
import com.shepshook.shopper.viewmodel.CartViewModel;

import java.util.function.Consumer;

public class CartActivity extends AppCompatActivity {

    private CartViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView totalPrice = findViewById(R.id.cartTotalPrice);

        viewModel.cartItems.observe(this, x -> {
            CartItemsAdapter adapter = new CartItemsAdapter(x,
                    item -> viewModel.removeOneFrom(item.cartItem),
                    item -> viewModel.addOneTo(item.cartItem),
                    item -> viewModel.deleteItem(item.cartItem));

            totalPrice.setText("$" + viewModel.getTotalPrice());

            recyclerView.setAdapter(adapter);
        });
    }


}