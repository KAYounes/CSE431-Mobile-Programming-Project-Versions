package com.example.aklny_v30.ui.s5_home_screen;

import androidx.recyclerview.widget.DiffUtil;

import com.example.aklny_v30.models.RestaurantModel;

import java.util.List;
import java.util.Objects;

public class DiffUtil_RestaurantList extends DiffUtil.Callback {
    private final List<RestaurantModel> oldList;
    private final List<RestaurantModel> newList;

    public DiffUtil_RestaurantList(List<RestaurantModel> oldList, List<RestaurantModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition).getKey(), newList.get(newItemPosition).getKey());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
