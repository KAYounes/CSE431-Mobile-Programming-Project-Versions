package com.example.aklny_v30;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.aklny_v30.databinding.ActivityTestBinding;

public class TestActivity extends AppCompatActivity {
    ActivityTestBinding binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
        binder = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        binder.btnSignUpWithPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PRINT", "binder.btnSignUpWithPassword > clicked");
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("PRINT", "onActivityResult > " + data.getStringExtra("state"));
    }
}