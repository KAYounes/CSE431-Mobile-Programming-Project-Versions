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

public class MenuItemRecyclerViewAdapter extends RecyclerView.Adapter<MenuItemRecyclerViewAdapter.ChoiceViewHolder> {

    public class ChoiceViewHolder extends RecyclerView.ViewHolder{
        private TextView menuItemChoiceTitle;
        private LinearLayout menuItemChoicesContainer;

        public ChoiceViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.menuItemChoiceTitle = itemView.findViewById(R.id.menu_item_choice_name);
            this.menuItemChoicesContainer = itemView.findViewById(R.id.menu_item_choices_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(MenuItemRecyclerViewAdapter.this.recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            MenuItemRecyclerViewAdapter.this.recyclerViewInterface.onRecyclerViewClick(position);
                        }
                    }
                }
            });
        }
    }


    private ArrayList<MenuItemChoiceModel> choices;
    private final RecyclerViewInterface recyclerViewInterface;

    public MenuItemRecyclerViewAdapter(ArrayList<MenuItemChoiceModel> choices, RecyclerViewInterface recyclerViewInterface) {
        this.choices = choices;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_item_choice, parent, false);
        ChoiceViewHolder choiceViewHolder = new ChoiceViewHolder(view, recyclerViewInterface);
        return choiceViewHolder;
    }

    public void onBindViewHolder(@NonNull ChoiceViewHolder holder, int position) {
        MenuItemChoiceModel choice = choices.get(position);

        String choiceTitle = choice.getChoice_name();
        holder.menuItemChoiceTitle.setText(choiceTitle);

        ArrayList<MenuItemChoiceValueModel> choiceValues = choice.getChoice_values();

        Context context = holder.itemView.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.card_restaurant_small, null);
        for (MenuItemChoiceValueModel choiceValue: choiceValues) {
            View view = inflater.inflate(R.layout.menu_item_choice_value, null);
            createChoiceValue(view, choiceValue, choice.getType());
            Log.d("PRINT", "getItemCount 2> " + getItemCount());
            holder.menuItemChoicesContainer.addView(view);
        }
        Log.d("PRINT", "getItemCount 3> " + getItemCount());


    }

    private void createChoiceValue(View view, MenuItemChoiceValueModel item, MenuItemChoiceModel.choiceType type){
        TextView choice_value = view.findViewById(R.id.choice_value);
        TextView choice_fee = view.findViewById(R.id.choice_fee);
        ImageView choice_value_selector = view.findViewById(R.id.choice_value_selector);
        choice_value.setText(item.getChoice_value());

        DecimalFormat choice_fee_decimal_format = new DecimalFormat("00.00");
        String formatted_choice_fee = choice_fee_decimal_format.format(item.getChoice_fees());
        choice_fee.setText(formatted_choice_fee);

        if(type == MenuItemChoiceModel.choiceType.MULTI_CHOICE){
            choice_value_selector.setImageDrawable(view.getResources().getDrawable(R.drawable.mulit_choice_selector));
        }
    }

    @Override
    public int getItemCount() {
//        try {
//            int item_count = choices.size();
//            return item_count;
//        }catch (Exception e){
//            return 0;
//        }
        return choices.size();
    }
}
