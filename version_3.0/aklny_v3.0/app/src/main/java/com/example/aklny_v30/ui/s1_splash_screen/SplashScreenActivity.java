package com.example.aklny_v30.ui.s1_splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivitySplashScreenBinding;
import com.example.aklny_v30.ui.s2_landing_screen.LandingScreenActivity;
import com.example.aklny_v30.ui.s5_home_screen.Activity_HomeScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {
    ActivitySplashScreenBinding binder;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        binder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SplashScreenActivity.this, "Signing Out", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = firebaseAuth.getCurrentUser();




        handler.postDelayed(() -> {
            if(user != null)
            {
                // User is logged in, start home screen activity.
                Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashScreenActivity.this, Activity_HomeScreen.class));
            }
            else
            {
                // User is not logged in, start landing screen activity.
                Toast.makeText(this, "You are not logged in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashScreenActivity.this, LandingScreenActivity.class));
            }

            finish();
        }, 2000);


    }
}