package com.example.aklny_v20;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.SubmenuViewHolder> {

    public class SubmenuViewHolder extends RecyclerView.ViewHolder{
        private TextView submenuTitle;
        private LinearLayout submenuItems;

        public SubmenuViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.submenuTitle = itemView.findViewById(R.id.sub_menu_title);
            this.submenuItems = itemView.findViewById(R.id.sub_menu_items);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(MenuRecyclerViewAdapter.this.recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            MenuRecyclerViewAdapter.this.recyclerViewInterface.onRecyclerViewClick(position);
                        }
                    }
                }
            });
        }
    }


    private ArrayList<SubmenuModel> submenus;
    private final RecyclerViewInterface recyclerViewInterface;

    public MenuRecyclerViewAdapter(ArrayList<SubmenuModel> submenus, RecyclerViewInterface recyclerViewInterface) {
        this.submenus = submenus;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public SubmenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.submenu, parent, false);
        SubmenuViewHolder submenuViewHolder = new SubmenuViewHolder(view, recyclerViewInterface);
        return submenuViewHolder;
    }

    public void onBindViewHolder(@NonNull SubmenuViewHolder holder, int position) {
        SubmenuModel submenu = submenus.get(position);

        String submenuTitle = submenu.getTitle();
        holder.submenuTitle.setText(submenuTitle);

        ArrayList<MenuItemModel> submenuItems2 = submenu.getSubMenuItems();

        Context context = holder.itemView.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.card_restaurant_small, null);
        for (MenuItemModel card: submenuItems2) {
            View view = inflater.inflate(R.layout.card_menu_item, null);
            createCard(view, card);
            Log.d("print", "1> " + getItemCount());
            holder.submenuItems.addView(view);
        }


    }

    private void createCard(View view, MenuItemModel item){
        TextView title = view.findViewById(R.id.card_title);
        TextView card_description = view.findViewById(R.id.card_description);
        TextView card_price = view.findViewById(R.id.card_price);
        ImageView card_thumbnail = view.findViewById(R.id.card_thumbnail);

        title.setText(item.getItem_name());
        card_description.setText(item.getItem_description());

        DecimalFormat formatted_price = new DecimalFormat("00.00");
        String formatted_price_string = formatted_price.format(item.getItem_price());

        card_price.setText(formatted_price_string);
        card_thumbnail.setImageDrawable(view.getResources().getDrawable(item.getItem_thumbnail()));
    }

    @Override
    public int getItemCount() {
//        try {
//            int item_count = submenus.size();
//            return item_count;
//        }catch (Exception e){
//            return 0;
//        }
        return submenus.size();
    }
}
