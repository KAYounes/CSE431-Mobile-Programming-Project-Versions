package com.example.aklny_v20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.RestaurantCardViewHolder> {

    public class RestaurantCardViewHolder extends RecyclerView.ViewHolder{
        private TextView card_title, card_description, card_rating, card_delivery_fee;
        private ImageView card_thumbnail;
        public RestaurantCardViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.card_title = itemView.findViewById(R.id.card_title);
            this.card_description = itemView.findViewById(R.id.card_description);
            this.card_rating = itemView.findViewById(R.id.card_rating);
            this.card_delivery_fee = itemView.findViewById(R.id.card_delivery_fee);
            this.card_thumbnail = itemView.findViewById(R.id.card_thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(RestaurantRecyclerViewAdapter.this.recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            RestaurantRecyclerViewAdapter.this.recyclerViewInterface.onRecyclerViewClick(position);
                        }
                    }
                }
            });
        }
    }

    private ArrayList<RestaurantModel> restaurantModels;
    private final RecyclerViewInterface recyclerViewInterface;

    public RestaurantRecyclerViewAdapter(ArrayList<RestaurantModel> restaurantModels, RecyclerViewInterface recyclerViewInterface) {
        this.restaurantModels = restaurantModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RestaurantCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_restaurant_small, parent, false);
        RestaurantCardViewHolder restaurantCardViewHolder = new RestaurantCardViewHolder(view, recyclerViewInterface);
        return restaurantCardViewHolder;
    }

    //    @NonNull
//    @Override
//    public RestaurantCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.card_restaurant, parent, false);
//        RestaurantCardViewHolder restaurantCardViewHolder = new RestaurantCardViewHolder(view);
//        return null;
//    }




    public void onBindViewHolder(@NonNull RestaurantCardViewHolder holder, int position) {
        RestaurantModel restaurant = restaurantModels.get(position);
        String name = restaurant.getName(), description = restaurant.getDescription();
        double rate = restaurant.getRating(), delivery_fee = restaurant.getDeliveryFee();
        int thumbnail = restaurant.getThumbnail();

        holder.card_title.setText(name);
        holder.card_description.setText(description);
        holder.card_rating.setText(Double.toString(rate));
        holder.card_delivery_fee.setText(Double.toString(delivery_fee));
        holder.card_thumbnail.setImageDrawable(holder.itemView.getResources().getDrawable(thumbnail));
    }

    @Override
    public int getItemCount() {
        int item_count = restaurantModels.size();
        return item_count;
    }
}
