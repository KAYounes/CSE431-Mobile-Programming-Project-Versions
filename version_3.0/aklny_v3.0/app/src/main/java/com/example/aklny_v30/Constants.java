package com.example.aklny_v30;

public class Constants {
    public static final String BASE_URL = "https://www.timeapi.io/api/Time/current/";


    /** RecyclerViewOnClickListener Tags **/
    public static final String ACTION_REMOVE = "REMOVE_ITEM_FROM_CART";
    public static final String ACTION_INCREMENT = "INCREMENT_ITEM_COUNT_IN_CART";
    public static final String ACTION_DECREMENT = "DECREMENT_ITEM_COUNT_IN_CART";

    /** Intent Keys **/
    public static final String INTENT_KEY_RESTAURANT_OBJ = "RESTAURANT";
    public static final String INTENT_KEY_PAYMENT = "PAYMENT";
    public static final String INTENT_KEY_MENU_KEY = "MENU_KEY";
    public static final String INTENT_KEY_ORDER_KEY =  "ORDER_KEY";


    /** Firebase APIs **/
    public static final String FIREBASE_BASE_URL = "https://aklny-v3-default-rtdb.firebaseio.com/";
    public static final String FIREBASE_MENU_NODE = "menus";
    public static final String FIREBASE_MENU_ITEMS_NODE = "menuItems";
    public static final String FIREBASE_ORDER_NODE = "orders";

    /** Regex Patterns **/
    public static final String PATTERN_RESTAURANT_NAME = "^[a-zA-Z0-9 -]{3,30}$";
    public static final String PATTERN_RESTAURANT_DESCRIPTION = "^[a-zA-Z0-9 ,_:;?'`/’'.!\\-()+]{3,500}$";
    public static final String PATTERN_RESTAURANT_PHONENUMBER = "^\\+201[0-9]{9}$";
    public static final String PATTERN_RESTAURANT_ADDRESS = "^.{10,90}$";
    public static final String PATTERN_MENU_TITLE = "^[a-zA-Z -_]{3,60}$";

    /** Shared Preference Keys **/
    public static final String SHARED_PREFERENCE_KEY_DEFAULT_GATE = "DEFAULT_KEY";
    public static final String SHAREDPREFERENCE_PUT_KEY_GATE = "GATE_NUMBER";

    /** Delivery Times **/
    public static final int FIRST_PERIOD = 10;
    public static final int SECOND_PERIOD = 18;
}
