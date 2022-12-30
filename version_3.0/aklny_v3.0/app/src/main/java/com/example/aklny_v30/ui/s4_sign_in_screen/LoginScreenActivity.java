package com.example.aklny_v30.ui.s4_sign_in_screen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.ui.s3_sign_up_screen.SignUpScreenActivity;
import com.example.aklny_v30.ui.transparent_activities.AuthorizationActivity;
import com.example.aklny_v30.databinding.ActivityLoginScreenBinding;
import com.example.aklny_v30.ui.s5_home_screen.Activity_HomeScreen;

public class LoginScreenActivity extends AppCompatActivity {
    ActivityLoginScreenBinding binder;
    int notEmptyFlag = 0;

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

        binder.btnLogin.setEnabled(false);

        binder.textInputEditEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(checkEmptyEmailOrPassword())
                {
                    binder.btnLogin.setEnabled(false);
                }
                else
                {
                    binder.btnLogin.setEnabled(true);
                }
            }
        });

        binder.textInputEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(checkEmptyEmailOrPassword())
                {
                    binder.btnLogin.setEnabled(false);
                }
                else
                {
                    binder.btnLogin.setEnabled(true);
                }
            }
        });

        binder.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateEmail() || !validatePassword())
                {
                    Toast.makeText(LoginScreenActivity.this, "Fill credentials first", Toast.LENGTH_SHORT).show();
                    return;
                }
                signInWithPassword();
            }
        });

        binder.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreenActivity.this, SignUpScreenActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Log.d("PRINT", "onActivityResult > "
//                + requestCode
//                + " - " + resultCode
//                + " - " + data.getStringExtra(AuthorizationActivity.RESULT_KEY));

        if(requestCode == AuthorizationActivity.AUTHENTICATION_REQUEST_CODE)
        {
            String result = data.getStringExtra(AuthorizationActivity.RESULT_KEY);
            if(result.equals(AuthorizationActivity.AUTHENTICATION_SUCCESS))
            {
                Toast.makeText(LoginScreenActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginScreenActivity.this, Activity_HomeScreen.class));

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                sendBroadcast(broadcastIntent);
                LoginScreenActivity.this.finish();
            }
            else
            {
                Toast.makeText(LoginScreenActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }

//            Toast.makeText(LoginScreenActivity.this, data.getStringExtra(AuthorizationActivity.RESULT_KEY), Toast.LENGTH_SHORT).show();
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

//        if(!validateEmail(email))
//        {
//            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(!validatePassword(password))
//        {
//            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
//            return;
//        }

        Intent signUpWithPasswordIntent = new Intent(LoginScreenActivity.this, AuthorizationActivity.class);
        signUpWithPasswordIntent.putExtra("ACTION", AuthorizationActivity.SIGN_IN_WITH_PASSWORD);
        signUpWithPasswordIntent.putExtra("EMAIL", email);
        signUpWithPasswordIntent.putExtra("PASSWORD", password);
        startActivityForResult(signUpWithPasswordIntent, AuthorizationActivity.AUTHENTICATION_REQUEST_CODE);
    }

    private boolean validateEmail() {
        String email = binder.textInputEditEmail.getText().toString();
        email.trim();
//        String emailPattern = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        return (!email.isEmpty());
    }

    private boolean validatePassword() {
        String password = binder.textInputEditPassword.getText().toString();
        return !password.isEmpty();
    }

    public boolean checkEmptyEmailOrPassword(){
        return binder.textInputEditEmail.getText().toString().isEmpty()
                || binder.textInputEditPassword.getText().toString().isEmpty();
    }

}