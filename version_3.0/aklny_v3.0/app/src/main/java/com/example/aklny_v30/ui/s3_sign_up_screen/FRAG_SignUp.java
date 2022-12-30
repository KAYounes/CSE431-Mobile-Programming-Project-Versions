package com.example.aklny_v30.ui.s3_sign_up_screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aklny_v30.databinding.FragmentSignUpChoicesBinding;
import com.example.aklny_v30.ui.ui_utilities.ReplaceFragmentCallback;
import com.example.aklny_v30.ui.s4_sign_in_screen.ACT_LoginScreen;

public class FRAG_SignUp extends Fragment {
    private FragmentSignUpChoicesBinding binder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binder = FragmentSignUpChoicesBinding.inflate(inflater, container, false);

        binder.btnPasswordEmailSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragmentCallback replaceFragmentCallback = (ReplaceFragmentCallback) getActivity();
                replaceFragmentCallback.replaceFragmentFromActivity(new FRAG_SignUpWithPassword());
            }
        });

        binder.btnGoogleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragmentCallback replaceFragmentCallback = (ReplaceFragmentCallback) getActivity();
                replaceFragmentCallback.replaceFragmentFromActivity(new FRAG_SignUpWithGoogle());
            }
        });


        binder.btnAlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginScreenIntent = new Intent(requireActivity(), ACT_LoginScreen.class);
                startActivity(loginScreenIntent);
                requireActivity().finish();
            }
        });

        return binder.getRoot();
    }
}