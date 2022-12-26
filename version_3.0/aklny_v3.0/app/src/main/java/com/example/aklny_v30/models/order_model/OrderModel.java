package com.example.aklny_v30.models.order_model;

import androidx.annotation.NonNull;

import com.example.aklny_v30.models.cart.CartItemModel;

import java.util.List;

public class OrderModel {
    private String
            orderKey,
            restaurantName,
            restaurantLogoURL,
            subTotal,
            deliveryFee,
            timeOfOrder,
            timeOfDelivery,
            deliveryGate;

    private List<CartItemModel> cart;


    public OrderModel() {
    }


    @NonNull
    @Override
    public String toString() {
        return "Order is: "
                + orderKey
                + " | " + restaurantName
                + " | " + subTotal
                + " | " + deliveryFee
                + " | " + timeOfOrder
                + " | " + timeOfDelivery
                + " | " + deliveryGate;
    }

    /** Getters and Setters **/
    public String getOrderKey() {return orderKey;}
    public void setOrderKey(String orderKey) {this.orderKey = orderKey;}

    public String getRestaurantName() {return restaurantName;}
    public void setRestaurantName(String restaurantName) {this.restaurantName = restaurantName;}

    public String getRestaurantLogoURL() {return restaurantLogoURL;}
    public void setRestaurantLogoURL(String restaurantLogoURL) {this.restaurantLogoURL = restaurantLogoURL;}

    public String getSubTotal() {return subTotal;}
    public void setSubTotal(String subTotal) {this.subTotal = subTotal;}

    public String getDeliveryFee() {return deliveryFee;}
    public void setDeliveryFee(String deliveryFee) {this.deliveryFee = deliveryFee;}

    public String getTimeOfOrder() {return timeOfOrder;}
    public void setTimeOfOrder(String timeOfOrder) {this.timeOfOrder = timeOfOrder;}

    public String getTimeOfDelivery() {return timeOfDelivery;}
    public void setTimeOfDelivery(String timeOfDelivery) {this.timeOfDelivery = timeOfDelivery;}

    public String getDeliveryGate() {return deliveryGate;}
    public void setDeliveryGate(String deliveryGate) {this.deliveryGate = deliveryGate;}

    public List<CartItemModel> getCart() {return cart;}
    public void setCart(List<CartItemModel> cart) {this.cart = cart;}
}
