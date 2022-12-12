package com.example.aklny_v20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    CartItemRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<CartItemModel> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        recyclerView = findViewById(R.id.cart_items_recycler_view);
        cartItems.add(new CartItemModel("Item 1", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 10.00, 1, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 2", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 20.00, 2, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 3", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 30.00, 3, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 4", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 40.00, 4, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 5", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 50.00, 5, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 6", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 60.00, 6, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 7", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 70.00, 7, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 8", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 80.00, 8, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 9", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 90.00, 9, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 10", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 100.00, 10, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 11", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 110.00, 11, R.drawable.burger_image_4));
        cartItems.add(new CartItemModel("Item 12", "Size:Small, Extras:Rice Pudding + Mushroom + Salad, Beverage: Orange Juice", 120.00, 12, R.drawable.burger_image_4));
        updatePayment();

        recyclerViewAdapter = new CartItemRecyclerViewAdapter(cartItems, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Toast.makeText(MainActivity4.this, "CHANGE", Toast.LENGTH_SHORT).show();
                updatePayment();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                updatePayment();
            }
        });

    }

    @Override
    public void onRecyclerViewClick(int position) {
//        Toast.makeText(this, cartItems.get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    public void updatePayment(){
        TextView subtotal = findViewById(R.id.payment_summary_subtotal_value);
        TextView delivery_fee = findViewById(R.id.payment_summary_delivery_fee_value);
        TextView total = findViewById(R.id.payment_summary_total);

        double subtotal_value = 0;
        double delivery_fee_value = 0;
        for (CartItemModel cartItem:cartItems) {
            subtotal_value += cartItem.getBasePrice() * cartItem.getCount();
        }
        subtotal.setText(Double.toString(subtotal_value));
        total.setText(Double.toString(delivery_fee_value + subtotal_value));
    }
}