package com.example.aklny_v30;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aklny_v30.databinding.HeaderRestaurantScreenBinding;
import com.example.aklny_v30.databinding.InnerRecyclerViewBinding;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OuterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int HEADER = 1;
    private static final int MENU = 2;

    private List<String> headerFields;
    private List<MenuModel> menus;
    private RecyclerViewOnClickListener onClickListener;

    public OuterRecyclerViewAdapter(List<MenuModel> menus, RecyclerViewOnClickListener onClickListener) {
        this.menus = menus;
        this.onClickListener = onClickListener;
    }

    public void setMenusList(List<MenuModel> menuModels) {
        menus = menuModels;
        addHeader();
        notifyDataSetChanged();
    }

    public void addHeader(){
        menus.add(0, null);
    }
    public void setHeader(List<String> headerFields){
        this.headerFields = headerFields;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
        {
            return HEADER;
        }
        else
        {
            return MENU;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("OUTER ADAPTER", "onCreateViewHolder");
        Log.d("OUTER ADAPTER", "viewType > " + viewType);

        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;

        switch (viewType){
            case HEADER:
                Log.d("OUTER ADAPTER", "viewType > HEADER");

                HeaderRestaurantScreenBinding binderHeader;
                binderHeader = HeaderRestaurantScreenBinding.inflate(
                        LayoutInflater.from(context), parent, false);
                viewHolder = new HeaderViewHolder(binderHeader, onClickListener);
                return viewHolder;
            case MENU:
                Log.d("OUTER ADAPTER", "viewType > MENU");
                InnerRecyclerViewBinding binderMenu;
                binderMenu = InnerRecyclerViewBinding.inflate(
                        LayoutInflater.from(context), parent, false);
                viewHolder = new MenuViewHolder(binderMenu, onClickListener);
                return viewHolder;
            default:
                throw new RuntimeException("View Type not found");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Log.d("OUTER ADAPTER", "onBindViewHolder > viewType > " + viewType);
        switch (viewType){
            case HEADER:
                Log.d("OUTER ADAPTER", "onBindViewHolder > viewType > HEADER");
                binderHeader((HeaderViewHolder) holder, position);
                break;
            case MENU:
                Log.d("OUTER ADAPTER", "onBindViewHolder > viewType > MENU");
                binderMenu((MenuViewHolder) holder, position);
                break;
        }
    }

    private void binderHeader(HeaderViewHolder holder, int position) {
        Picasso.get().load(headerFields.get(0))
                .placeholder(R.drawable.icon_logo_placeholder_100dp)
                .error(R.drawable.icon_failed_to_load_logo_100dp)
                .into(holder.binder.restaurantThumbnail);
        holder.binder.restaurantName.setText(headerFields.get(1));
        holder.binder.restaurantDescription.setText(headerFields.get(2));
        holder.binder.restaurantAddress.setText(headerFields.get(3));
        holder.binder.restaurantPhoneNumber.setText(headerFields.get(4));
        holder.binder.restaurantDeliveryFee.setText(headerFields.get(5));
        holder.binder.restaurantRating.setText(headerFields.get(6));

        holder.binder.btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onRecyclerViewClick(0);
            }
        });
    }

    private void binderMenu(MenuViewHolder holder, int position) {
        InnerRecyclerViewAdapter innerAdapter = new InnerRecyclerViewAdapter(menus.get(position).getMenuItems(), onClickListener);
        holder.binder.menuTitle.setText(menus.get(position).getTitle());
        holder.binder.menuItemsList.setAdapter(innerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.binder.getRoot().getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setReverseLayout(false);
        holder.binder.menuItemsList.setLayoutManager(layoutManager);
    }

    @Override
    public int getItemCount() {
        try
        {
            return menus.size();
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{
        private HeaderRestaurantScreenBinding binder;

        public HeaderViewHolder(@NonNull HeaderRestaurantScreenBinding binder, RecyclerViewOnClickListener onClickListener) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    class MenuViewHolder extends RecyclerView.ViewHolder{
        private InnerRecyclerViewBinding binder;

        public MenuViewHolder(@NonNull InnerRecyclerViewBinding binder, RecyclerViewOnClickListener onClickListener) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
