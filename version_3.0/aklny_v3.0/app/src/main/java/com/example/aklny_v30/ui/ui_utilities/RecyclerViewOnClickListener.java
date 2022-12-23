package com.example.aklny_v30.ui.ui_utilities;

public interface RecyclerViewOnClickListener {
    void onRecyclerViewClick(int position);
    void onRecyclerViewClickPayload(Object payload);
    void onRecyclerViewClickPayload(String tag, Object payload);
}
