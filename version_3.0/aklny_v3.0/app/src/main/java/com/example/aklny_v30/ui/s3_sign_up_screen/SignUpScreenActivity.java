package com.example.aklny_v30.ui.s3_sign_up_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.aklny_v30.databinding.ActivitySignUpScreenBinding;
import com.example.aklny_v30.ui.ui_utilities.ReplaceFragmentCallback;

public class SignUpScreenActivity extends AppCompatActivity implements ReplaceFragmentCallback {
    private ActivitySignUpScreenBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivitySignUpScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
    }

    @Override
    public void replaceFragmentFromActivity(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binder.fragmentContainer.getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}