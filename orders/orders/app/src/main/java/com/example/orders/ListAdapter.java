package com.example.orders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orders.databinding.CardBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    public List<OrderModel> list = new ArrayList<>();
    private OnClick onClick;

    public ListAdapter(List<OrderModel> list, OnClick onClick) {
        this.list = list;
        this.onClick = onClick;
    }

    public void setData(List<OrderModel> orders){
        this.list  = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        CardBinding binder;
        binder = CardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderModel order = list.get(position);
        CardBinding binder = holder.binder;

        String[] status = binder.getRoot().getResources().getStringArray(R.array.status);
        ArrayAdapter<String> adapter = new ArrayAdapter(binder.getRoot().getContext(), R.layout.dropdown_item, status);
        binder.orderStatus.setAdapter(adapter);
        Log.d("PRINT", "adapter.getItem(0) > " + adapter.getItem(0));
        switch (order.getOrderStatus()) {
            case NOT_CONFIRMED:
                binder.orderStatus.setText("NOT_CONFIRMED", false);
                break;
            case PREPARING:
                binder.orderStatus.setText("PREPARING", false);
                break;
            case DELIVERING:
                binder.orderStatus.setText("DELIVERING", false);
                break;
            case DELIVERED:
                binder.orderStatus.setText("DELIVERED", false);
                break;
        }
//        binder.orderStatus.setText(, false);

        binder.resName.setText(order.getRestaurantName());
        binder.orderDateTime.setText(order.getDateOfOrder() + " - " + order.getTimeOfOrder());
        binder.userName.setText(order.getUserName());

        Picasso.get().load(order.getRestaurantLogoURL())
                .placeholder(R.drawable.icon_logo_placeholder)
                    .error(R.drawable.icon_logo_error)
                .into(binder.resImage);

        binder.orderStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(adapter.getItem(i) == order.getOrderStatus().toString()){
                    return;
                }

                switch (adapter.getItem(i)) {
                    case "NOT_CONFIRMED":
                        Log.d("PRINT", "onItemClick > " + 1);
                        onClick.onClick(order.getUserKey(), order.getOrderKey(), OrderModel.OrderStatus.NOT_CONFIRMED);
                        break;
                    case "PREPARING":
                        Log.d("PRINT", "onItemClick > " + 2);
                        onClick.onClick(order.getUserKey(), order.getOrderKey(), OrderModel.OrderStatus.PREPARING);
                        break;
                    case "DELIVERING":
                        Log.d("PRINT", "onItemClick > " + 3);
                        onClick.onClick(order.getUserKey(), order.getOrderKey(), OrderModel.OrderStatus.DELIVERING);
                        break;
                    case "DELIVERED":
                        Log.d("PRINT", "onItemClick > " + 4);
                        onClick.onClick(order.getUserKey(), order.getOrderKey(), OrderModel.OrderStatus.DELIVERED);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        CardBinding binder;

        public MyViewHolder(CardBinding binder){
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
