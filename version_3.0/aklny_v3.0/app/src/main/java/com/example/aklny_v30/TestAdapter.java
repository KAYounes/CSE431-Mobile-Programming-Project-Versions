package com.example.aklny_v30;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aklny_v30.databinding.RvItemRestaurantCardLargeBinding;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.s5_home_screen.RestaurantListRecyclerViewAdapter;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.CardViewHolder> {
    public static enum cardType{SMALL_CARD, LARGE_CARD};
    private cardType cardDisplayType = cardType.SMALL_CARD;
    private List<RestaurantModel> restaurants;
    private RecyclerViewOnClickListener onClickListener;
    private boolean rendered = false;

    public TestAdapter(List<RestaurantModel> restaurants, RecyclerViewOnClickListener onClickListener) {
        this.restaurants = restaurants;
        this.onClickListener = onClickListener;
    }

    public void toggleCardTypeDisplay()
    {
        if(cardDisplayType == cardType.SMALL_CARD)
        {
            cardDisplayType = cardType.LARGE_CARD;
        }
        else
        {
            cardDisplayType = cardType.SMALL_CARD;
        }
//        Log.d("PRINT", "> " + cardDisplayType);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d("ADAPTER", "onCreateViewHolder");

        Context context = parent.getContext();
        CardViewHolder viewHolder;
        RvItemRestaurantCardLargeBinding binder;

        binder = RvItemRestaurantCardLargeBinding.inflate(
                LayoutInflater.from(context), parent, false);
        viewHolder = new CardViewHolder(binder, onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position)
    {
        Log.d("ADAPTER", "onBindViewHolder");
//        if(rendered == false)
//        {
//        }
//        rendered = true;
        bindToCard(holder, position);

        switch (cardDisplayType){
            case SMALL_CARD:
                holder.binder.restaurantThumbnail.setVisibility(View.GONE);
                break;
            case LARGE_CARD:
                holder.binder.restaurantThumbnail.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void bindToCard(CardViewHolder holder, int position)
    {
        Log.d("ADAPTER", "bindToCardLarge");

        RestaurantModel restaurant = restaurants.get(position);

        Picasso.get().load(restaurant.getThumbnail()).into(holder.binder.restaurantThumbnail);
        Picasso.get().load(restaurant.getLogo()).into(holder.binder.restaurantLogo);
//
//        holder.binder.restaurantThumbnail.setImageDrawable(holder.binder.getRoot().getResources().getDrawable(R.drawable.food_image___1_));
//        holder.binder.restaurantLogo.setImageDrawable(holder.binder.getRoot().getResources().getDrawable(R.drawable.logo_1));

        holder.binder.restaurantName.setText(restaurant.getName());
        holder.binder.restaurantDescription.setText(restaurant.getDescription());

        DecimalFormat rating_decimal_format = new DecimalFormat("0.0");
        String formatted_rate = rating_decimal_format.format(restaurant.getRating());
        holder.binder.restaurantRating.setText(formatted_rate);

        DecimalFormat delivery_fee_decimal_format = new DecimalFormat("00.00");
        String formatted_delivery_fee = delivery_fee_decimal_format.format(restaurant.getDelivery_fee());
        holder.binder.restaurantDeliveryFee.setText(formatted_delivery_fee);

        holder.binder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener != null){
                    onClickListener.onRecyclerViewClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return restaurants.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder
    {
        private RvItemRestaurantCardLargeBinding binder;
        public CardViewHolder(@NonNull RvItemRestaurantCardLargeBinding binder, RecyclerViewOnClickListener onClickListener)
        {
            super(binder.getRoot());
            this.binder = binder;
        }

    }
}
