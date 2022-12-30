package com.example.aklny_v30.ui.s7_cart_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aklny_v30.Constants;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.BgCartIsEmptyBinding;
import com.example.aklny_v30.databinding.FooterCartScreenPaymentBinding;
import com.example.aklny_v30.databinding.RvItemCartItemCardBinding;
import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class RVAdapter_CartItemsList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int EMPTY_CART = 3;
    private final int CART_ITEM = 2;
    private final int FOOTER = 1;
    private List<CartItemModel> cartItems;
    private final RecyclerViewOnClickListener onClickListener;
    private HashMap<String, String> footerFields;

    public RVAdapter_CartItemsList(List<CartItemModel> cartItems, RecyclerViewOnClickListener onClickListener) {
        this.cartItems = cartItems;
        this.onClickListener = onClickListener;
    }


    public void setCartItems(List<CartItemModel> cartItems) {
        this.cartItems = cartItems;
        addFooter();
        notifyDataSetChanged();
    }

    public void addFooter(){
        cartItems.add(0, null);
    }
    public void setFooter(HashMap<String, String> footerFields){
        this.footerFields = footerFields;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        switch (viewType){
            case FOOTER:
                FooterCartScreenPaymentBinding binderFooter;
                binderFooter = FooterCartScreenPaymentBinding
                        .inflate(LayoutInflater.from(context), parent, false);
                return new FooterViewHolder(binderFooter, onClickListener);
            case CART_ITEM:
                RvItemCartItemCardBinding binderCartItem;
                binderCartItem = RvItemCartItemCardBinding
                        .inflate(LayoutInflater.from(context), parent, false);
                return new CartItemViewHolder(binderCartItem, onClickListener);
            case EMPTY_CART:
                BgCartIsEmptyBinding binderEmptyCart;
                binderEmptyCart = BgCartIsEmptyBinding
                        .inflate(LayoutInflater.from(context), parent, false);
                return new EmptyCartViewHolder(binderEmptyCart, onClickListener);
            default:
                throw new RuntimeException("View Type unknown");
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case FOOTER:
                bindFooter((FooterViewHolder) holder, position);
                break;
            case CART_ITEM:
                bindCartItem((CartItemViewHolder) holder, position);
                break;
            case EMPTY_CART:
                binderEmptyCart((EmptyCartViewHolder) holder, position);
                break;
        }
    }

    private void binderEmptyCart(EmptyCartViewHolder holder, int position) {
    }

//    public CartItemModel getItem(int position){
//        return cartItems.get(position);
//    }

    private void bindFooter(FooterViewHolder holder, int position){
        FooterCartScreenPaymentBinding binder = holder.binder;
        binder.cartSubtotal.setText(footerFields.get("subtotal"));
        binder.deliverFee.setText(footerFields.get("deliveryFee"));
        binder.total.setText(footerFields.get("total"));
    }

    private void bindCartItem(CartItemViewHolder holder, int position){
        CartItemModel cartItem = cartItems.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("00.00");

        RvItemCartItemCardBinding binder = holder.binder;
        binder.itemName.setText(cartItem.getName());
        binder.itemDescription.setText(cartItem.getDescription());

        String formatted_price = decimalFormat.format(cartItem.getPrice());
        binder.itemPrice.setText(formatted_price);
        binder.itemCount.setText(Integer.toString(cartItem.getQuantity()));
        binder.itemCounter.setText(Integer.toString(cartItem.getQuantity()));


        String formatted_subtotal = decimalFormat.format(cartItem.getQuantity() * cartItem.getPrice());
        binder.itemSubtotal.setText(formatted_subtotal);

        Picasso.get().load(cartItem.getThumbnail())
                .placeholder(R.drawable.icon_logo_placeholder_100dp)
                .error(R.drawable.icon_failed_to_load_logo_100dp)
                .into(binder.itemThumbnail);

        binder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onRecyclerViewClickPayload(Constants.ACTION_REMOVE, cartItem.getKey());
            }
        });

        binder.btnDecrementCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onRecyclerViewClickPayload(Constants.ACTION_DECREMENT, cartItem.decrementQuantityAndReturn());
            }
        });

        binder.btnIncrementCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onRecyclerViewClickPayload(Constants.ACTION_INCREMENT, cartItem.incrementQuantityAndReturn());
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
//        Log.d("PRINT", "count, " + getItemCount());

        if(getItemCount() == 1){
//            Log.d("PRINT", "EMPTY_CART");
            return EMPTY_CART;
        }

        if(position == 0) {
            return FOOTER;
        }

        return CART_ITEM;

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class EmptyCartViewHolder extends RecyclerView.ViewHolder{

        public EmptyCartViewHolder(@NonNull BgCartIsEmptyBinding binder, RecyclerViewOnClickListener onClickListener) {
            super(binder.getRoot());
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{
        private final FooterCartScreenPaymentBinding binder;

        public FooterViewHolder(@NonNull FooterCartScreenPaymentBinding binder, RecyclerViewOnClickListener onClickListener) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder{
        private final RvItemCartItemCardBinding binder;

        public CartItemViewHolder(@NonNull RvItemCartItemCardBinding binder, RecyclerViewOnClickListener onClickListener) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
