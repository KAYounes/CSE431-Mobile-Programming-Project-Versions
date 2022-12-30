package com.example.aklny_v30.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.databinding.ActivityViewUsersBinding;
import com.example.aklny_v30.models.UserModel;
import com.example.aklny_v30.repos.UsersRepo;

import java.util.List;

public class ACT_ViewUsers extends AppCompatActivity {
    UsersRepo usersRepo;
    ActivityViewUsersBinding binder;
    String usersString;

    BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityViewUsersBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                finish();
            }
        }, intentFilter);
        

        usersRepo = new UsersRepo(getApplication());
        usersRepo.getUsers().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                usersString = "";
                binder.users.setText("");
                for(UserModel user: userModels){
                    usersString += user.display();
                    usersString += "\n\n";
                }

                binder.users.setText(usersString);
            }
        });

        binder.btnDeleteUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ACT_ViewUsers.this, "Deleting", Toast.LENGTH_SHORT).show();
                usersRepo.deleteAll();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}