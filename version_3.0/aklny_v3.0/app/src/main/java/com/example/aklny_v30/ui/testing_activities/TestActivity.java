package com.example.aklny_v30.ui.testing_activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aklny_v30.databinding.ActivityTestBinding;
import com.example.aklny_v30.ui.transparent_activities.AuthorizationActivity;

public class TestActivity extends AppCompatActivity {
    ActivityTestBinding binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        binder.btnSignUpWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signUpWithPasswordIntent = new Intent(TestActivity.this, AuthorizationActivity.class);
                signUpWithPasswordIntent.putExtra("ACTION", 10);
                signUpWithPasswordIntent.putExtra("FIRSTNAME", "John");
                signUpWithPasswordIntent.putExtra("LASTNAME", "Smith");
                signUpWithPasswordIntent.putExtra("PHONENUMBER", "+201111111111");
                startActivityForResult(signUpWithPasswordIntent, 2);
            }
        });

        binder.btnSignUpWithPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signUpWithPasswordIntent = new Intent(TestActivity.this, AuthorizationActivity.class);
                signUpWithPasswordIntent.putExtra("ACTION", 20);
                signUpWithPasswordIntent.putExtra("FIRSTNAME", "John");
                signUpWithPasswordIntent.putExtra("LASTNAME", "Smith");
                signUpWithPasswordIntent.putExtra("PHONENUMBER", "+201111111111");
                signUpWithPasswordIntent.putExtra("EMAIL", binder.inputEmail.getText().toString());
                signUpWithPasswordIntent.putExtra("PASSWORD", binder.inputPassword.getText().toString());
                startActivityForResult(signUpWithPasswordIntent, 2);
            }
        });

        binder.btnSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signUpWithPasswordIntent = new Intent(TestActivity.this, AuthorizationActivity.class);
                signUpWithPasswordIntent.putExtra("ACTION", 30);
                startActivityForResult(signUpWithPasswordIntent, AuthorizationActivity.AUTHENTICATION_REQUEST_CODE);
            }
        });

        binder.btnSignInWithPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signUpWithPasswordIntent = new Intent(TestActivity.this, AuthorizationActivity.class);
                signUpWithPasswordIntent.putExtra("ACTION", 40);
                signUpWithPasswordIntent.putExtra("EMAIL", binder.inputEmail.getText().toString());
                signUpWithPasswordIntent.putExtra("PASSWORD", binder.inputPassword.getText().toString());
                startActivityForResult(signUpWithPasswordIntent, AuthorizationActivity.AUTHENTICATION_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}