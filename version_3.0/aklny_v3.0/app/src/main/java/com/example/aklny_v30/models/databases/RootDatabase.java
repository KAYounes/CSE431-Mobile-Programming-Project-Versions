package com.example.aklny_v30.models.databases;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.models.user_model.UsersTableDAO;

@Database(entities = {UserModel.class}, version = 1, exportSchema = false)
public abstract class RootDatabase extends RoomDatabase {
    public abstract UsersTableDAO usersTableDAO();
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
//                            .addCallback(RoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback RoomDatabaseCallback = new RoomDatabase.Callback(){
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
    };

}
