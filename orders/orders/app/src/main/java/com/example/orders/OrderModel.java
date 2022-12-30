package com.example.orders;


import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.text.DecimalFormat;
import java.util.List;

public class OrderModel {
    private String
            orderKey,
            restaurantName,
            userName,
            userKey,
            restaurantLogoURL,
            subTotal,
            deliveryFee,
            timeOfOrder,
            dateOfOrder,
            timeOfDelivery,
            deliveryGate;
    private OrderStatus orderStatus = OrderStatus.NOT_CONFIRMED;
    private List<CartItemModel> cart;
    public OrderModel() {
    }

    @NonNull
    @Override
    public String toString() {
        return "Order = "
                + " |restaurantName: " + restaurantName
                + " |userName: " + userName
                + " |timeOfDelivery: " + timeOfDelivery
                + " |dateOfOrder: " + dateOfOrder
                + " |deliveryGate: " + deliveryGate;
    }

    public String getTotal(){
        double subtotal = Double.parseDouble(this.subTotal);
        double deliveryFee = Double.parseDouble(this.deliveryFee);
        return new DecimalFormat("00.00").format(subtotal+deliveryFee);
    }

    /** Getters and Setters **/
    public String getOrderKey() {return orderKey;}

    public void setOrderKey(String orderKey) {this.orderKey = orderKey;}

    public String getRestaurantName() {return restaurantName;}

    public void setRestaurantName(String restaurantName) {this.restaurantName = restaurantName;}

    @Exclude
    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    @Exclude
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getRestaurantLogoURL() {return restaurantLogoURL;}

    public void setRestaurantLogoURL(String restaurantLogoURL) {this.restaurantLogoURL = restaurantLogoURL;}

    public String getSubTotal() {return subTotal;}

    public void setSubTotal(String subTotal) {this.subTotal = subTotal;}

    public String getDeliveryFee() {return deliveryFee;}

    public void setDeliveryFee(String deliveryFee) {this.deliveryFee = deliveryFee;}

    public String getTimeOfOrder() {return timeOfOrder;}

    public void setTimeOfOrder(String timeOfOrder) {this.timeOfOrder = timeOfOrder;}

    public String getDateOfOrder() {return dateOfOrder;}

    public void setDateOfOrder(String dateOfOrder) {this.dateOfOrder = dateOfOrder;}

    public String getTimeOfDelivery() {return timeOfDelivery;}

    public void setTimeOfDelivery(String timeOfDelivery) {this.timeOfDelivery = timeOfDelivery;}

    public String getDeliveryGate() {return deliveryGate;}

    public void setDeliveryGate(String deliveryGate) {this.deliveryGate = deliveryGate;}

    public OrderStatus getOrderStatus() {return orderStatus;}

    public void setOrderStatus(OrderStatus orderStatus) {this.orderStatus = orderStatus;}

    public List<CartItemModel> getCart() {return cart;}

    public void setCart(List<CartItemModel> cart) {this.cart = cart;}
    public enum OrderStatus {NOT_CONFIRMED, PREPARING, DELIVERING, DELIVERED}
}
