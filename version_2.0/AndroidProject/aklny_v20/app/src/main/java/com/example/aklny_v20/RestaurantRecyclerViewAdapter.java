package com.example.aklny_v20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class RestaurantCardSmallViewHolder extends RecyclerView.ViewHolder{
        private TextView card_title, card_description, card_rating, card_delivery_fee;
        private ImageView card_logo;
        public RestaurantCardSmallViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.card_title = itemView.findViewById(R.id.card_title);
            this.card_description = itemView.findViewById(R.id.card_description);
            this.card_rating = itemView.findViewById(R.id.card_rating);
            this.card_delivery_fee = itemView.findViewById(R.id.card_delivery_fee);
            this.card_logo = itemView.findViewById(R.id.card_logo);

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

    public class RestaurantCardLargeViewHolder extends RecyclerView.ViewHolder{
        private TextView card_title, card_description, card_rating, card_delivery_fee;
        private ImageView card_logo, card_thumbnail;
        public RestaurantCardLargeViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.card_title = itemView.findViewById(R.id.card_title);
            this.card_description = itemView.findViewById(R.id.card_description);
            this.card_rating = itemView.findViewById(R.id.card_rating);
            this.card_delivery_fee = itemView.findViewById(R.id.card_delivery_fee);
            this.card_logo = itemView.findViewById(R.id.card_logo);
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

    public ArrayList<RestaurantCardModel> restaurantCardModels;
    private static final int CARD_SMALL = 1, CARD_LARGE=2;
    private final RecyclerViewInterface recyclerViewInterface;
    public enum cardType {CARD_SMALL, CARD_LARGE};
    public RestaurantCardModel.cardType type;

    public RestaurantRecyclerViewAdapter(ArrayList<RestaurantCardModel> restaurantCardModels, RecyclerViewInterface recyclerViewInterface) {
        this.restaurantCardModels = restaurantCardModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if(viewType == CARD_SMALL){
            view = inflater.inflate(R.layout.card_restaurant_small, parent, false);
            RestaurantCardSmallViewHolder restaurantCardViewHolder = new RestaurantCardSmallViewHolder(view, recyclerViewInterface);
            return restaurantCardViewHolder;
        }
        else if (viewType == CARD_LARGE){
            view = inflater.inflate(R.layout.card_restaurant_large, parent, false);
            RestaurantCardLargeViewHolder restaurantCardViewHolder = new RestaurantCardLargeViewHolder(view, recyclerViewInterface);
            return restaurantCardViewHolder;
        }else{
            throw new RuntimeException("UNKNOWN CARD TYPE");
        }
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);

        RestaurantCardModel item = restaurantCardModels.get(position);
        if (type == RestaurantCardModel.cardType.CARD_SMALL) {
            return CARD_SMALL;
        } else if (type == RestaurantCardModel.cardType.CARD_LARGE) {
            return CARD_LARGE;
        } else {
            return -1;
        }
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case CARD_SMALL:
                bindToCardSmall((RestaurantCardSmallViewHolder) holder, position);
                break;
            case CARD_LARGE:
                bindToCardLarge((RestaurantCardLargeViewHolder) holder, position);
                break;
            default:
                break;
        }
    }

    private void bindToCardSmall(RestaurantCardSmallViewHolder holder, int position){
        RestaurantCardModel restaurant = restaurantCardModels.get(position);

        String
                name = restaurant.getName(),
                description = restaurant.getDescription();

        double
                rate = restaurant.getRating(),
                delivery_fee = restaurant.getDeliveryFee();

        int
                logo = restaurant.getLogo();

        holder.card_title.setText(name);
        holder.card_description.setText(description);

        DecimalFormat rating_decimal_format = new DecimalFormat("0.0");
        String formatted_rate = rating_decimal_format.format(rate);
        holder.card_rating.setText(formatted_rate);

        DecimalFormat delivery_fee_decimal_format = new DecimalFormat("00.00");
        String formatted_delivery_fee = delivery_fee_decimal_format.format(delivery_fee);
        holder.card_delivery_fee.setText(formatted_delivery_fee);
        holder.card_logo.setImageDrawable(holder.itemView.getResources().getDrawable(logo));
    }

    private void bindToCardLarge(RestaurantCardLargeViewHolder holder, int position){
        RestaurantCardModel restaurant = restaurantCardModels.get(position);

        String
                name = restaurant.getName(),
                description = restaurant.getDescription();

        double
                rate = restaurant.getRating(),
                delivery_fee = restaurant.getDeliveryFee();

        int
                logo = restaurant.getLogo(),
                thumbnail = restaurant.getThumbnail();

        holder.card_title.setText(name);
        holder.card_description.setText(description);
        holder.card_rating.setText(Double.toString(rate));
        holder.card_delivery_fee.setText(Double.toString(delivery_fee));
        holder.card_logo.setImageDrawable(holder.itemView.getResources().getDrawable(logo));
        holder.card_thumbnail.setImageDrawable(holder.itemView.getResources().getDrawable(thumbnail));
    }

    @Override
    public int getItemCount() {
        try {
            int item_count = restaurantCardModels.size();
            return item_count;
        }catch (Exception e){
            return 0;
        }
    }
}
