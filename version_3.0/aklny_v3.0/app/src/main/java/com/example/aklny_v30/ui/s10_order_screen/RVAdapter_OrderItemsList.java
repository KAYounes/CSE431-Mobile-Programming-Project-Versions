package com.example.aklny_v30.ui.s10_order_screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aklny_v30.databinding.RvItemOrderItemCardBinding;
import com.example.aklny_v30.databinding.RvItemPreviousOrdersCardBinding;
import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;

import java.util.List;

public class RVAdapter_OrderItemsList extends RecyclerView.Adapter<RVAdapter_OrderItemsList.OrderItemViewHolder>{
    private List<CartItemModel> items;
    private RecyclerViewOnClickListener onClickListener;

    public RVAdapter_OrderItemsList(List<CartItemModel> items) {
        this.items = items;
        this.onClickListener = onClickListener;
    }

    public void setData(List<CartItemModel> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RvItemOrderItemCardBinding binder;
        binder = RvItemOrderItemCardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new OrderItemViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        CartItemModel item = items.get(position);
//        Log.d("PRINT", "item > " + item);
        RvItemOrderItemCardBinding binder = holder.binder;
        binder.itemName.setText(item.getName());
        binder.itemQuantity.setText(Integer.toString(item.getQuantity()));
        binder.total.setText(item.getTotal());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder{
        private RvItemOrderItemCardBinding binder;

        public OrderItemViewHolder(RvItemOrderItemCardBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
