package com.example.aklny_v30;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aklny_v30.databinding.ActivityLandingScreenBinding;
import com.example.aklny_v30.databinding.ActivitySignUpScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpScreenActivity extends AppCompatActivity {
    private ActivitySignUpScreenBinding b;

    FirebaseAuth firebaseAuth;
    EditText tv_email, tv_password;
    Button btn_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySignUpScreenBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);

        b.btnAlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginScreenIntent = new Intent(SignUpScreenActivity.this, LoginScreenActivity.class);
                startActivity(loginScreenIntent);
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

//        tv_email = findViewById(R.id.tv_email);
//        tv_password = findViewById(R.id.tv_password);
//        btn_sign_up = findViewById(R.id.btn_sign_up);
//
//        btn_sign_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = tv_email.getText().toString();
//                String password = tv_password.getText().toString();
//                if(email != null && password != null){
//                    singUp(email, password);
//                }
//            }
//        });
    }

    public void singUp(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignUpScreenActivity.this, "SIGN UP SUCCESS", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SignUpScreenActivity.this, "SIGN UP FAILED", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}