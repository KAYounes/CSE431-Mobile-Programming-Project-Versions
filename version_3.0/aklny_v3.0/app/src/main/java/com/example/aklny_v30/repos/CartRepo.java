package com.example.aklny_v30.repos;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.cart.CartTableDAO;
import com.example.aklny_v30.models.databases.RootDatabase;

import java.util.List;

public class CartRepo {
    private CartTableDAO dao;
    private LiveData<List<CartItemModel>> cart;

    public CartRepo(Application application)
    {
        RootDatabase db = RootDatabase.getDatabase(application);
        this.dao = db.cartTableDAO();
        this.cart = dao.getCart();
    }

    public LiveData<List<CartItemModel>> getCart(){
        return cart;
    }
    public void deleteCart(){
        new deleteAsyncTask(dao).execute();
    }
    public void addItem(CartItemModel cartItem){
        Log.d("PRINT", "CartRepo > addItem > item > " + cartItem.toString());
        new insertAsyncTask(dao).execute(cartItem);
    }
    public void removeItem(String key){
        new removeItemAsyncTask(dao).execute(key);
    }
    public void updateItem(CartItemModel item){
        new updateAsyncTask(dao).execute(item);
    }

    private class insertAsyncTask extends AsyncTask<CartItemModel, Void, CartItemModel>
    {
        private CartTableDAO asyncTaskDAO;

        insertAsyncTask(CartTableDAO doa)
        {
            asyncTaskDAO = doa;
        }

        @Override
        protected CartItemModel doInBackground(final CartItemModel... params)
        {

            try
            {
                asyncTaskDAO.addItem(params[0]);
                return params[0];
            }
            catch (Exception e)
            {
                Log.e("PRINT", "CartRepo > insertAsyncTask > " + e.getMessage());
                CartItemModel cartItemModel = asyncTaskDAO.getCartItem(params[0].getKey());
                asyncTaskDAO.updateItem(cartItemModel.incrementQuantityAndReturn());
            }

            return null;
        }

//        @Override
//        protected void onPostExecute(CartItemModel newUser) {
//            super.onPostExecute(newUser);
//            Log.d("PRINT", "User >> " + newUser.toString());
//            user.setValue(newUser);
//        }
    }

    private static class updateAsyncTask extends AsyncTask<CartItemModel, Void, Void>
    {
        private CartTableDAO asyncTaskDAO;

        updateAsyncTask(CartTableDAO doa)
        {
            asyncTaskDAO = doa;
        }

        @Override
        protected  Void doInBackground(final CartItemModel... params)
        {

            try
            {
                asyncTaskDAO.updateItem(params[0]);
            }
            catch (Exception e)
            {
                Log.e("PRINT", "CartRepo > updateAsyncTask > " + e.getMessage());
            }

            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private CartTableDAO asyncTaskDAO;

        deleteAsyncTask(CartTableDAO doa)
        {
            asyncTaskDAO = doa;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            try
            {
                asyncTaskDAO.deleteCart();
            }
            catch (Exception e)
            {
                Log.e("PRINT", "CartRepo > deleteAsyncTask > " + e.getMessage());
            }

            return null;
        }
    }

    private static class removeItemAsyncTask extends AsyncTask<String, Void, Void>
    {
        private CartTableDAO asyncTaskDAO;

        removeItemAsyncTask(CartTableDAO doa)
        {
            asyncTaskDAO = doa;
        }

        @Override
        protected Void doInBackground(String... keys)
        {

            try
            {
                asyncTaskDAO.removeItem(keys[0]);
            }
            catch (Exception e)
            {
                Log.e("PRINT", "CartRepo > removeItemAsyncTask > " + e.getMessage());
            }

            return null;
        }
    }
}

