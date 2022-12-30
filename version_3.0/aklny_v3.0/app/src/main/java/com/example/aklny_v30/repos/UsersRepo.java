package com.example.aklny_v30.repos;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.databases.RootDatabase;
import com.example.aklny_v30.models.UserModel;
import com.example.aklny_v30.DAOs.UsersTableDAO;

import java.util.List;

public class UsersRepo
{
    private final UsersTableDAO dao;
    private final MutableLiveData<UserModel> user = new MutableLiveData<>();
//    private final LiveData<List<UserModel>> users;

    public UsersRepo(Application application)
    {
        RootDatabase db = RootDatabase.getDatabase(application);
        dao = db.usersTableDAO();
    }

    public void deleteAll(){
        new deleteAsyncTask(dao).execute();
    }
    public LiveData<List<UserModel>> getUsers()
    {
        return dao.getAllUsers();
    }

    public LiveData<UserModel> getUser(String uid)
    {
        return dao.getUser(uid);
    }

    public void updateUser(UserModel user){
        new updateAsyncTask(dao).execute(user);
    }

    public void addUser(UserModel user){
        new insertAsyncTask(dao).execute(user);
    }

    private class insertAsyncTask extends AsyncTask<UserModel, Void, UserModel>
    {
        private final UsersTableDAO asyncTaskDAO;

        insertAsyncTask(UsersTableDAO doa)
        {
            asyncTaskDAO = doa;
        }

        @Override
        protected UserModel doInBackground(final UserModel... params)
        {

            try
            {

                asyncTaskDAO.addUser(params[0]);
                return params[0];
            }
            catch (Exception e)
            {
                Log.e("PRINT", "Insert User error > " + e.getLocalizedMessage());
                asyncTaskDAO.updateUser(params[0]);
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
        private final UsersTableDAO asyncTaskDAO;

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

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private final UsersTableDAO asyncTaskDAO;

        deleteAsyncTask(UsersTableDAO doa)
        {
            asyncTaskDAO = doa;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            try
            {
                asyncTaskDAO.deleteAll();
            }
            catch (Exception e)
            {
                Log.e("PRINT", "deleteAll error");
            }

            return null;
        }
    }
}
