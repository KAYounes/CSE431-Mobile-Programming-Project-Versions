package com.example.aklny_v30.models.user_model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.databases.RootDatabase;

import java.util.List;

public class UsersRepository
{
    private UsersTableDAO dao;
    private MutableLiveData<UserModel> user = new MutableLiveData<>();

    public UsersRepository(Application application)
    {
        RootDatabase db = RootDatabase.getDatabase(application);
        dao = db.usersTableDAO();
    }

    public LiveData<UserModel> getUser(String uid)
    {
        return dao.getUser(uid);
    }

    public boolean userExists(String uid)
    {
        return getUser(uid).getValue() != null;
    }

    public void updateUser(UserModel user){
        new updateAsyncTask(dao).execute(user);
    }

    public void addUser(UserModel user){
        new insertAsyncTask(dao).execute(user);
    }

    private class insertAsyncTask extends AsyncTask<UserModel, Void, UserModel>
    {
        private UsersTableDAO asyncTaskDAO;

        insertAsyncTask(UsersTableDAO doa)
        {
            asyncTaskDAO = doa;
        }

        @Override
        protected UserModel doInBackground(final UserModel... params)
        {

            try
            {
                Log.d("PRINT", "insertAsyncTask > uid > " + params[0].getAuth_uid());
                asyncTaskDAO.addUser(params[0]);
                return params[0];
            }
            catch (Exception e)
            {
                Log.e("PRINT", "Insert User error > " + e.getLocalizedMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(UserModel newUser) {
            super.onPostExecute(newUser);
            user.setValue(newUser);
        }
    }

    private static class updateAsyncTask extends AsyncTask<UserModel, Void, Void>
    {
        private UsersTableDAO asyncTaskDAO;

        updateAsyncTask(UsersTableDAO doa)
        {
            asyncTaskDAO = doa;
        }

        @Override
        protected  Void doInBackground(final UserModel... params)
        {

            try
            {
                asyncTaskDAO.updateUser(params[0]);
            }
            catch (Exception e)
            {
                Log.e("PRINT", "Update User error");
            }

            return null;
        }
    }
}
