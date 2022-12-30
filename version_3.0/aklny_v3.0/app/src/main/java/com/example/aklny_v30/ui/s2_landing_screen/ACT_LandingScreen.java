package com.example.aklny_v30.ui.s2_landing_screen;

import androidx.annotation.Nullable;
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
import com.example.aklny_v30.models.UserModel;
import com.example.aklny_v30.repos.UsersRepo;
import com.example.aklny_v30.ui.s4_sign_in_screen.ACT_LoginScreen;
import com.example.aklny_v30.ui.s3_sign_up_screen.ACT_SignUpScreen;
import com.example.aklny_v30.ui.s5_home_screen.Activity_HomeScreen;
import com.example.aklny_v30.ui.transparent_activities.AuthorizationActivity;

import java.util.List;

public class ACT_LandingScreen extends AppCompatActivity {
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

        binder.btnGoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpWithPasswordIntent = new Intent(ACT_LandingScreen.this, AuthorizationActivity.class);
                signUpWithPasswordIntent.putExtra("ACTION", AuthorizationActivity.SIGN_IN_WITH_PASSWORD);
                signUpWithPasswordIntent.putExtra("EMAIL", "safe@mail.com");
                signUpWithPasswordIntent.putExtra("PASSWORD", "#Abc123#");
                startActivityForResult(signUpWithPasswordIntent, AuthorizationActivity.AUTHENTICATION_REQUEST_CODE);
            }
        });

        binder.btnLandingScreenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable())
                {
                    Intent loginScreenIntent = new Intent(ACT_LandingScreen.this, ACT_LoginScreen.class);
                    startActivity(loginScreenIntent);
                }
                else
                {
                    Toast.makeText(ACT_LandingScreen.this, "No Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binder.btnLandingScreenSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable())
                {
                    Intent signUpScreenIntent = new Intent(ACT_LandingScreen.this, ACT_SignUpScreen.class);
                    startActivity(signUpScreenIntent);
                }
                else
                {
                    // replace with dialog
                    Toast.makeText(ACT_LandingScreen.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AuthorizationActivity.AUTHENTICATION_REQUEST_CODE)
        {
            String result = data.getStringExtra(AuthorizationActivity.RESULT_KEY);
            if(result.equals(AuthorizationActivity.AUTHENTICATION_SUCCESS))
            {
                Toast.makeText(ACT_LandingScreen.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ACT_LandingScreen.this, Activity_HomeScreen.class));

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                sendBroadcast(broadcastIntent);
                ACT_LandingScreen.this.finish();
            }
            else
            {
                Toast.makeText(ACT_LandingScreen.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
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