package com.example.aklny_v30.ui.s2_landing_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.databinding.ActivityLandingScreenBinding;
import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.repos.UsersRepo;
import com.example.aklny_v30.ui.s4_sign_in_screen.LoginScreenActivity;
import com.example.aklny_v30.ui.s3_sign_up_screen.SignUpScreenActivity;

import java.util.List;

public class LandingScreenActivity extends AppCompatActivity {
    private ActivityLandingScreenBinding binder;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityLandingScreenBinding.inflate(getLayoutInflater());
        View view = binder.getRoot();
        setContentView(view);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                finish();
            }
        }, intentFilter);

        UsersRepo usersRepo = new UsersRepo(getApplication());
        usersRepo.getUsers().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                Log.d("USERS", "\nUpdate ----------------------\n");
                for (UserModel user: userModels) {
                    Log.d("USERS", "- " + user.toString());
                }
            }
        });

        binder.btnLandingScreenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable())
                {
                    Intent loginScreenIntent = new Intent(LandingScreenActivity.this, LoginScreenActivity.class);
                    startActivity(loginScreenIntent);
                }
                else
                {
                    Toast.makeText(LandingScreenActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binder.btnLandingScreenSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable())
                {
                    Intent signUpScreenIntent = new Intent(LandingScreenActivity.this, SignUpScreenActivity.class);
                    startActivity(signUpScreenIntent);
                }
                else
                {
                    // replace with dialog
                    Toast.makeText(LandingScreenActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private boolean isNetworkAvailable(){
        try
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if(connectivityManager != null){
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }
        catch (NullPointerException e)
        {
            return false;
        }
    }
}