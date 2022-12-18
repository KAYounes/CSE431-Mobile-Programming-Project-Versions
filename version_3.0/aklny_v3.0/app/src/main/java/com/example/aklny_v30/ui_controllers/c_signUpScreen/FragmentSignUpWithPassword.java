package com.example.aklny_v30.ui_controllers.c_signUpScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aklny_v30.databinding.FragmentSignUpWithPasswordBinding;
import com.example.aklny_v30.ui_controllers.e_homeScreen.HomeScreenActivity;
import com.example.aklny_v30.viewModels.SignUpViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class FragmentSignUpWithPassword extends Fragment {
    private static final int REQUEST_CODE_SIGN_IN = 1000;
    private SignUpViewModel viewModel;
    private FragmentSignUpWithPasswordBinding binder;
    private String helperMessage;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binder = FragmentSignUpWithPasswordBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);


        binder.btnSignUpWithPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });


        return binder.getRoot();
    }

    private void signUp() {
        firstName = binder.textInputEditFirstName.getText().toString();
        lastName = binder.textInputEditLastName.getText().toString();
        phoneNumber = binder.textInputEditPhoneNumber.getText().toString();
        email = binder.textInputEditEmail.getText().toString();
        password = binder.textInputEditPassword.getText().toString();

        if(validateAccountDetails())
        {
            viewModel.authenticateUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d("PRINT", "sign up password successful");
                        helperMessage = "Successfully created your account.";
                        viewModel.addUserToDatabases(firstName, lastName, phoneNumber, email);
                        startActivity(new Intent(requireActivity(), HomeScreenActivity.class));
                        requireActivity().finish();
                    }
                    else
                    {
                        // Either user already exists, or failure.
                        Log.d("PRINT", "sign up password failed");
                        if(viewModel.doesUserExists())
                        {
                            Toast.makeText(requireActivity(), "User Exists", Toast.LENGTH_SHORT).show();
                            helperMessage = "This account already exists";
                            binder.textHelpMessage.setText(helperMessage);
                        }
                        else
                        {
                            Toast.makeText(requireActivity(), "Unknown signup failure", Toast.LENGTH_SHORT).show();
                            helperMessage = "Signup failed, please check your connection.";
                            binder.textHelpMessage.setText(helperMessage);
                        }
                    }
                }
            });
        }

        binder.textHelpMessage.setText(helperMessage);
    }

    private boolean validateAccountDetails(){
        return validateName() && validatePhoneNumber() && validateEmail() && validatePassword();
    }

    private boolean validateName() {
//        String firstName, lastName;
//        firstName = binder.textInputEditFirstName.getText().toString();
//        lastName = binder.textInputEditLastName.getText().toString();

        if(firstName.isEmpty() || lastName.isEmpty()){
            helperMessage = "Your name cannot be empty";
            return false;
        }

        if(!firstName.matches("[a-zA-Z ,.'-]{2,15}") && lastName.matches("[a-zA-Z ,.'-]{2,15}")){
            helperMessage = "Name is invalid";
            return  false;
        }

        return true;
    }

    private boolean validatePhoneNumber() {
//        String phoneNumber;
//        phoneNumber = binder.textInputEditPhoneNumber.getText().toString();

        if(phoneNumber.isEmpty()){
            helperMessage = "Phone number cannot be empty";
            return false;
        }

        if(!phoneNumber.matches("[0-9]{10}")){
            helperMessage = "Phone number is not a valid Egyptian Number";
            return  false;
        }

        return true;
    }

    private boolean validateEmail() {
//        String email;
//        email = binder.textInputEditEmail.getText().toString();

        if(email.isEmpty()){
            helperMessage = "Email cannot be empty";
            return false;
        }

        if(!email.matches("^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")){
            helperMessage = "Email is not a valid email";
            return  false;
        }

        return true;
    }

    private boolean validatePassword() {
//        String password;
//        password = binder.textInputEditPassword.getText().toString();

        if(password.isEmpty()){
            helperMessage = "Password cannot be empty";
            return false;
        }

        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&\\-+=()]).{8,20}$")){
            helperMessage = "Make sure your password is 8 characters long, has an uppercase and a lowercase letter, has a number, and has a symbol";
            return  false;
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder = null;
    }
}