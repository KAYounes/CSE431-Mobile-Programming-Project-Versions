package com.example.aklny_v30.ui.s9_previous_orders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.RvItemPreviousOrdersCardBinding;
import com.example.aklny_v30.models.order_model.OrderModel;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RCAdapter_PlacedOrdersList  extends RecyclerView.Adapter<RCAdapter_PlacedOrdersList.OrderViewHolder>{
    private List<OrderModel> orders;
    private RecyclerViewOnClickListener onClickListener;

    public RCAdapter_PlacedOrdersList(List<OrderModel> orders, RecyclerViewOnClickListener onClickListener) {
        this.orders = orders;
        this.onClickListener = onClickListener;
    }

    public void setData(List<OrderModel> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RvItemPreviousOrdersCardBinding binding;
        binding = RvItemPreviousOrdersCardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        RvItemPreviousOrdersCardBinding binding = holder.binding;
        OrderModel order = orders.get(position);
//        Log.d("PRINT", "oder > " + order);

        Picasso.get().load(order.getRestaurantLogoURL())
                .placeholder(R.drawable.icon_logo_placeholder_100dp)
                .error(R.drawable.icon_failed_to_load_logo_100dp)
                .into(binding.restaurantLogo);
        binding.currentOrderLabel.setVisibility(View.VISIBLE);

        String status = "";
        binding.textStatus.setTextColor(binding.getRoot().getResources().getColor(R.color._secondary_base));
        switch (order.getOrderStatus()){
            case NOT_CONFIRMED:
                status = "Confirming";
                break;
            case PREPARING:
                status = "Prepearing food";
                break;
            case DELIVERING:
                status = "On its way";
                break;
            case DELIVERED:
                status = "Order received";
                binding.textStatus.setTextColor(binding.getRoot().getResources().getColor(R.color.green));
                binding.currentOrderLabel.setVisibility(View.INVISIBLE);
                break;
        }

        binding.restaurantName.setText(order.getRestaurantName());
        binding.orderDate.setText(order.getDateOfOrder() + " - " + order.getTimeOfOrder());
        binding.orderStatus.setText(status);
        binding.orderTotal.setText(order.getTotal());

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onRecyclerViewClickPayload(order.getOrderKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        private RvItemPreviousOrdersCardBinding binding;

        public OrderViewHolder(@NonNull RvItemPreviousOrdersCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
