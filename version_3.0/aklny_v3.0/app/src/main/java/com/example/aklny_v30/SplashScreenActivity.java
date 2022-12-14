package com.example.aklny_v30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Handler handler = new Handler();

        handler.postDelayed(() -> {
            if(user != null)
            {
                // User is logged in, start home screen activity.
                startActivity(new Intent(SplashScreenActivity.this, LoginScreenActivity.class));
            }
            else
            {
                // User is not logged in, start landing screen activity.
                startActivity(new Intent(SplashScreenActivity.this, LandingScreenActivity.class));
            }

            finish();
        }, 500);


    }
}