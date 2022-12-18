package com.example.aklny_v30.ui_controllers.c_signUpScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.databinding.ActivitySignUpScreenBinding;
import com.example.aklny_v30.ui_controllers.ReplaceFragmentCallback;
import com.example.aklny_v30.ui_controllers.d_signInScreen.LoginScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpScreenActivity extends AppCompatActivity implements ReplaceFragmentCallback {
    private ActivitySignUpScreenBinding binder;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivitySignUpScreenBinding.inflate(getLayoutInflater());
        View view = binder.getRoot();
        setContentView(view);
    }

    public void singUp(String email, String password) {

    }

    @Override
    public void replaceFragmentFromActivity(Fragment fragment) {
        Toast.makeText(this, "replace", Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binder.fragmentContainer.getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}