package com.example.aklny_v30.ui.s6_restaurant_screen;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.HeaderRestaurantScreenBinding;
import com.example.aklny_v30.databinding.RvItemMenuBinding;
import com.example.aklny_v30.models.MenuModel;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class RVAdapter_ListOfMenus extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int HEADER = 1;
    private static final int MENU = 2;

    private HashMap<String, String> headerFields;
    private List<MenuModel> menus;
    private final RecyclerViewOnClickListener onClickListener;

    public RVAdapter_ListOfMenus(List<MenuModel> menus, RecyclerViewOnClickListener onClickListener)
    {
        this.menus = menus;
        this.onClickListener = onClickListener;
    }

    public void setMenusList(List<MenuModel> menuModels)
    {
        menus = menuModels;
        addHeader();
        notifyDataSetChanged();
    }

    public void addHeader()
    {
        menus.add(0, null);
    }
    public void setHeader(HashMap<String, String> headerFields)
    {
        this.headerFields = headerFields;
    }

    @Override
    public int getItemViewType(int position)
    {
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;

        switch (viewType)
        {
            case HEADER:
                HeaderRestaurantScreenBinding binderHeader;
                binderHeader = HeaderRestaurantScreenBinding.inflate(
                        LayoutInflater.from(context), parent, false);
                viewHolder = new HeaderViewHolder(binderHeader, onClickListener);
                return viewHolder;
            case MENU:
                RvItemMenuBinding binderMenu;
                binderMenu = RvItemMenuBinding.inflate(
                        LayoutInflater.from(context), parent, false);
                viewHolder = new MenuViewHolder(binderMenu, onClickListener);
                return viewHolder;
            default:
                throw new RuntimeException("View Type not found");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        int viewType = getItemViewType(position);
        switch (viewType)
        {
            case HEADER:
                binderHeader((HeaderViewHolder) holder, position);
                break;
            case MENU:
                binderMenu((MenuViewHolder) holder, position);
                break;
        }
    }

    private void binderHeader(HeaderViewHolder holder, int position)
    {
        Picasso.get().load(headerFields.get("Thumbnail"))
                .placeholder(R.drawable.icon_thumbnail_placeholder)
                .error(R.drawable.icon_failed_to_load_thumbnail)
                .into(holder.binder.restaurantThumbnail);
        holder.binder.restaurantName.setText(headerFields.get("Name"));
        holder.binder.restaurantDescription.setText(headerFields.get("Description"));
        holder.binder.restaurantAddress.setText(headerFields.get("Address"));
        holder.binder.restaurantPhoneNumber.setText(headerFields.get("PhoneNumber"));
        holder.binder.restaurantDeliveryFee.setText(headerFields.get("DeliveryFee"));
        holder.binder.restaurantRating.setText(headerFields.get("Rating"));

        holder.binder.btnAddMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onClickListener.onRecyclerViewClick(-1);
            }
        });
    }

    private void binderMenu(MenuViewHolder holder, int position)
    {
        RVAdapter_ListOfMenuItems innerAdapter = new RVAdapter_ListOfMenuItems(menus.get(position).getMenuItems(), onClickListener);
        holder.binder.menuTitle.setText(menus.get(position).getTitle());
        holder.binder.menuItemsList.setAdapter(innerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.binder.getRoot().getContext())
        {
            @Override
            public boolean canScrollVertically()
            {
                return false;
            }
        };
        layoutManager.setReverseLayout(false);
        holder.binder.menuItemsList.setLayoutManager(layoutManager);
    }

    @Override
    public int getItemCount()
    {
        try
        {
            return menus.size();
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        private final HeaderRestaurantScreenBinding binder;

        public HeaderViewHolder(@NonNull HeaderRestaurantScreenBinding binder, RecyclerViewOnClickListener onClickListener)
        {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    class MenuViewHolder extends RecyclerView.ViewHolder
    {
        private final RvItemMenuBinding binder;

        public MenuViewHolder(@NonNull RvItemMenuBinding binder, RecyclerViewOnClickListener onClickListener)
        {
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
