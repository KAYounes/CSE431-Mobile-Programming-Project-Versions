package com.example.aklny_v30.ui.ui_utilities;

import com.example.aklny_v30.models.menu_model.MenuItemModel;

public interface RecyclerViewOnClickListener {
    void onRecyclerViewClick(int position);
    void onRecyclerViewClickPayload(MenuItemModel payload);
}
