package com.example.aklny_v30.ui.s4_sign_in_screen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.databinding.ActivityLoginScreenBinding;
import com.example.aklny_v30.ui.s3_sign_up_screen.ACT_SignUpScreen;
import com.example.aklny_v30.ui.transparent_activities.AuthorizationActivity;
import com.example.aklny_v30.ui.s5_home_screen.Activity_HomeScreen;

public class ACT_LoginScreen extends AppCompatActivity {
    ActivityLoginScreenBinding binder;
    int notEmptyFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                binder.btnLogin.setEnabled(!checkEmptyEmailOrPassword());
            }
        });

        binder.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateEmail() || !validatePassword())
                {
                    Toast.makeText(ACT_LoginScreen.this, "Fill credentials first", Toast.LENGTH_SHORT).show();
                    return;
                }
                signInWithPassword();
            }
        });

        binder.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ACT_LoginScreen.this, ACT_SignUpScreen.class));
                finish();
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
                Toast.makeText(ACT_LoginScreen.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ACT_LoginScreen.this, Activity_HomeScreen.class));

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                sendBroadcast(broadcastIntent);
                ACT_LoginScreen.this.finish();
            }
            else
            {
                Toast.makeText(ACT_LoginScreen.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void signInWithGoogle() {
        Intent signUpWithPasswordIntent = new Intent(ACT_LoginScreen.this, AuthorizationActivity.class);
        signUpWithPasswordIntent.putExtra("ACTION", AuthorizationActivity.SIGN_IN_WITH_GOOGLE);
        startActivityForResult(signUpWithPasswordIntent, AuthorizationActivity.AUTHENTICATION_REQUEST_CODE);
//

    }

    private void signInWithPassword() {
        String email = binder.textInputEditEmail.getText().toString();
        String password = binder.textInputEditPassword.getText().toString();

        Intent signUpWithPasswordIntent = new Intent(ACT_LoginScreen.this, AuthorizationActivity.class);
        signUpWithPasswordIntent.putExtra("ACTION", AuthorizationActivity.SIGN_IN_WITH_PASSWORD);
        signUpWithPasswordIntent.putExtra("EMAIL", email);
        signUpWithPasswordIntent.putExtra("PASSWORD", password);
        startActivityForResult(signUpWithPasswordIntent, AuthorizationActivity.AUTHENTICATION_REQUEST_CODE);
    }

    private boolean validateEmail() {
        String email = binder.textInputEditEmail.getText().toString();
        email.trim();


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