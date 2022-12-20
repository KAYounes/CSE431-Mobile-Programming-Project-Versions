package com.example.aklny_v30.ui.s4_sign_in_screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.ui.transparent_activities.AuthorizationActivity;
import com.example.aklny_v30.databinding.ActivityLoginScreenBinding;
import com.example.aklny_v30.ui.s5_home_screen.HomeScreenActivity;

public class LoginScreenActivity extends AppCompatActivity {
    ActivityLoginScreenBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_screen);
        binder = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        // Sign In By Google Button
        binder.btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        binder.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithPassword();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("PRINT", "onActivityResult > "
                + requestCode
                + " - " + resultCode
                + " - " + data.getStringExtra(AuthorizationActivity.RESULT_KEY));

        if(requestCode == AuthorizationActivity.AUTHENTICATION_REQUEST_CODE){
            String result = data.getStringExtra(AuthorizationActivity.RESULT_KEY);
            if(result.equals(AuthorizationActivity.AUTHENTICATION_SUCCESS)){
                Toast.makeText(LoginScreenActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginScreenActivity.this, HomeScreenActivity.class));
                LoginScreenActivity.this.finish();
            }

            Toast.makeText(LoginScreenActivity.this, data.getStringExtra(AuthorizationActivity.RESULT_KEY), Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithGoogle() {
        Intent signUpWithPasswordIntent = new Intent(LoginScreenActivity.this, AuthorizationActivity.class);
        signUpWithPasswordIntent.putExtra("ACTION", AuthorizationActivity.SIGN_IN_WITH_GOOGLE);
        startActivityForResult(signUpWithPasswordIntent, AuthorizationActivity.AUTHENTICATION_REQUEST_CODE);
//
//        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
//                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
//
//        Intent signInIntent = googleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
//        dialog.startLoadingDialog();
    }

    private void signInWithPassword() {
        String email = binder.textInputEditEmail.getText().toString();
        String password = binder.textInputEditPassword.getText().toString();

        if(!validateEmail(email))
        {
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!validatePassword(password))
        {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent signUpWithPasswordIntent = new Intent(LoginScreenActivity.this, AuthorizationActivity.class);
        signUpWithPasswordIntent.putExtra("ACTION", AuthorizationActivity.SIGN_IN_WITH_PASSWORD);
        signUpWithPasswordIntent.putExtra("EMAIL", email);
        signUpWithPasswordIntent.putExtra("PASSWORD", password);
        startActivityForResult(signUpWithPasswordIntent, AuthorizationActivity.AUTHENTICATION_REQUEST_CODE);
    }

    private boolean validateEmail(@NonNull String email) {
        email.trim();
        String emailPattern = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        return (!email.isEmpty() && email.matches(emailPattern));
    }

    private boolean validatePassword(@NonNull String password) {
        return !password.isEmpty();
    }

}