package com.shepshook.shopper.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.shepshook.shopper.data.entity.Category;
import com.shepshook.shopper.databinding.ItemCategoryBinding;
import com.shepshook.shopper.fragment.CategoriesFragmentDirections;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final List<Category> list;

    public CategoriesAdapter(List<Category> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater, parent, false);

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
        private final ItemCategoryBinding binding;

        public ViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected void bind(Category item) {
            itemView.setOnClickListener((view) -> {
                NavDirections action = CategoriesFragmentDirections.actionCategoriesFragmentToProductListActivity(item.id);
                Navigation.findNavController(view).navigate(action);
            });
            binding.categoryTitle.setText(item.name);
        }
    }
}

