package com.example.aklny_v30.databases;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.DAOs.CartTableDAO;
import com.example.aklny_v30.models.UserModel;
import com.example.aklny_v30.DAOs.UsersTableDAO;

@Database(entities = {UserModel.class, CartItemModel.class}, version = 1, exportSchema = false)
public abstract class RootDatabase extends RoomDatabase {
    public abstract UsersTableDAO usersTableDAO();
    public abstract CartTableDAO cartTableDAO();
    private static RootDatabase INSTANCE;

    public static RootDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (RootDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            RootDatabase.class,
                            "Root Database")
                            .fallbackToDestructiveMigration()

                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback RoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final UsersTableDAO usersTableDAO;
        String[] words = {"Kareem", "Amr", "Younes"};

        PopulateDbAsync(RootDatabase db){
            usersTableDAO = db.usersTableDAO();
        }

        @Override
        protected Void doInBackground(final Void... voids) {

// Example
//            usersTableDAO.deleteAll();
//            for (int i=0; i <= words.length - 1; i++){
//                WordEntity word = new WordEntity(words[i], 1);
//                usersTableDAO.insert(word);
//            }
            return null;
        }
    }

}