package com.example.aklny_v20;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartItemRecyclerViewAdapter extends RecyclerView.Adapter<CartItemRecyclerViewAdapter.CartItemViewHolder> {

    public class CartItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView cartItemThumbnail, decrease_count, increase_count;
        private TextView cartItemName, cartItemDescription, cartItemCountInput,
                cartItemBasePrice, cartItemCount, cartItemTotalPrice, TextView, trash_item;

        public CartItemViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            cartItemThumbnail = itemView.findViewById(R.id.cart_item_thumbnail);
            cartItemName = itemView.findViewById(R.id.cart_item_name);
            cartItemDescription = itemView.findViewById(R.id.cart_item_description);
            cartItemCountInput = itemView.findViewById(R.id.cart_item_count_input);
            cartItemBasePrice = itemView.findViewById(R.id.cart_item_base_price);
            cartItemCount = itemView.findViewById(R.id.cart_item_count);
            cartItemTotalPrice = itemView.findViewById(R.id.cart_item_total_price);
            decrease_count = itemView.findViewById(R.id.item_count_decrease);
            increase_count = itemView.findViewById(R.id.item_count_increase);
            trash_item = itemView.findViewById(R.id.item_remove_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onClick(View view) {
                    if(CartItemRecyclerViewAdapter.this.recyclerViewInterface != null){
//                        Toast.makeText(view.getContext(), "1", Toast.LENGTH_SHORT).show();
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
//                            Toast.makeText(view.getContext(), "2", Toast.LENGTH_SHORT).show();
                            CartItemRecyclerViewAdapter.this.recyclerViewInterface.onRecyclerViewClick(position);


                            decrease_count.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    cartItems.get(position).decreaseCount();
                                    notifyDataSetChanged();
                                }
                            });

                            increase_count.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    cartItems.get(position).increaseCount();
                                    notifyDataSetChanged();
                                }
                            });


                            trash_item.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    cartItems.remove(position);
                                    notifyItemRemoved(position);
                                }
                            });

//                            decrease_count.setOnTouchListener(new View.OnTouchListener() {
//                                private android.os.Handler handler;
//
//                                @Override
//                                public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                                    switch(motionEvent.getAction()) {
//                                        case MotionEvent.ACTION_DOWN:
//                                            if (handler != null) return true;
//                                            handler = new android.os.Handler();
//                                            handler.post(updateCount);
//                                            break;
//                                        case MotionEvent.ACTION_UP:
//                                            if (handler == null) return true;
//                                            handler.removeCallbacks(updateCount);
//                                            handler = null;
//                                            break;
//                                        case MotionEvent.ACTION_CANCEL:
//                                            if (handler == null) return true;
//                                            handler.removeCallbacks(updateCount);
//                                            handler = null;
//                                            break;
//                                    }
//                                    return false;
//                                }
//
//                                Runnable updateCount = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        cartItems.get(position).decreaseCount();
//                                        notifyDataSetChanged();
//                                        handler.postDelayed(this, 100);
//                                    }
//                                };
//                            });
                        }
                    }
                }
            });
        }

    }

    private ArrayList<CartItemModel> cartItems;
    private RecyclerViewInterface recyclerViewInterface;

    public CartItemRecyclerViewAdapter(ArrayList<CartItemModel> cartItems, RecyclerViewInterface recyclerViewInterface) {
        this.cartItems = cartItems;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item_card, parent, false);
        CartItemViewHolder cartItemViewHolder = new CartItemViewHolder(view, recyclerViewInterface);
        return cartItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItemModel cartItem = cartItems.get(position);

        holder.cartItemThumbnail.setImageDrawable(holder.itemView.getResources().getDrawable(cartItem.getThumbnail()));
        holder.cartItemName.setText(cartItem.getName());
        holder.cartItemDescription.setText(cartItem.getDescription());
        holder.cartItemCount.setText(Integer.toString(cartItem.getCount()));
        holder.cartItemCountInput.setText(Integer.toString(cartItem.getCount()));
        holder.cartItemBasePrice.setText(Double.toString(cartItem.getBasePrice()));
        holder.cartItemTotalPrice.setText(Double.toString(cartItem.getBasePrice() * cartItem.getCount()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

}
