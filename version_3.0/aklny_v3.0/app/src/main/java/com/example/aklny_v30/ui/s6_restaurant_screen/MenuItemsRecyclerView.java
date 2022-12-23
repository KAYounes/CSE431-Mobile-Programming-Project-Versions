package com.example.aklny_v30.ui.s6_restaurant_screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.RvItemMenuItemCardBinding;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuItemsRecyclerView extends RecyclerView.Adapter<MenuItemsRecyclerView.MenuItemViewHolder> {
    private List<MenuItemModel> menuItems;
    private RecyclerViewOnClickListener onClickListener;

    public MenuItemsRecyclerView(List<MenuItemModel> menuItems, RecyclerViewOnClickListener onClickListener) {
        this.menuItems = menuItems;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RvItemMenuItemCardBinding binder;
        binder = RvItemMenuItemCardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MenuItemViewHolder(binder, onClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItemModel menuItem = menuItems.get(position);

        Picasso.get().load(menuItem.getThumbnail())
                .placeholder(R.drawable.icon_logo_placeholder_100dp)
                .error(R.drawable.icon_failed_to_load_logo_100dp)
                .into(holder.binder.itemThumbnail);
        holder.binder.itemName.setText(menuItem.getName());
        holder.binder.itemDescription.setText(menuItem.getDescription());
        holder.binder.itemPrice.setText(Double.toString(menuItem.getPrice()));

        holder.binder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onCLick", "inner > onClick");
                onClickListener.onRecyclerViewClickPayload(menuItem);
            }
        });
    }

    public MenuItemModel getItem(int position){
        return menuItems.get(position);
    }
    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder{
        private RvItemMenuItemCardBinding binder;

        public MenuItemViewHolder(@NonNull RvItemMenuItemCardBinding binder, RecyclerViewOnClickListener onClickListener) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
