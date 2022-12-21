package com.example.aklny_v30.ui.s1_splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.aklny_v30.R;
import com.example.aklny_v30.ui.s2_landing_screen.LandingScreenActivity;
import com.example.aklny_v30.ui.s5_home_screen.HomeScreenActivity;
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
                firebaseAuth.signOut();
                Toast.makeText(this, "You Are Already Logged In", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashScreenActivity.this, HomeScreenActivity.class));
            }
            else
            {
                // User is not logged in, start landing screen activity.
                Toast.makeText(this, "You Are Not Logged In", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashScreenActivity.this, LandingScreenActivity.class));
            }

            finish();
        }, 500);


    }
}