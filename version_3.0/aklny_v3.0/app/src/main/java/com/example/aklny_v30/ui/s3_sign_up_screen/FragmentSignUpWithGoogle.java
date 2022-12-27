package com.example.aklny_v30.ui.s3_sign_up_screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aklny_v30.ui.transparent_activities.AuthorizationActivity;
import com.example.aklny_v30.databinding.FragmentSignUpWithGoogleBinding;
import com.example.aklny_v30.ui.s5_home_screen.Activity_HomeScreen;

public class FragmentSignUpWithGoogle extends Fragment {
    private FragmentSignUpWithGoogleBinding binder;
    private String helperMessage;
    private String firstName;
    private String lastName;
    private String phoneNumber;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binder = FragmentSignUpWithGoogleBinding.inflate(inflater, container, false);

        binder.btnSignUpWithGoogle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               signIn();
           }
        });

        return binder.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Log.d("PRINT", "onActivityResult > "
//                + requestCode
//                + " - " + resultCode
//                + " - " + data.getStringExtra(AuthorizationActivity.RESULT_KEY));

        if(requestCode == AuthorizationActivity.AUTHENTICATION_REQUEST_CODE){
            String result = data.getStringExtra(AuthorizationActivity.RESULT_KEY);
            if(result.equals(AuthorizationActivity.AUTHENTICATION_SUCCESS)){
                Toast.makeText(requireActivity(), "Account Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity(), Activity_HomeScreen.class));
                requireActivity().finish();
                return;
            }

            Toast.makeText(requireActivity(), data.getStringExtra(AuthorizationActivity.RESULT_KEY), Toast.LENGTH_SHORT).show();
        }
    }


    private void signIn() {
        firstName = binder.textInputEditFirstName.getText().toString();
        lastName = binder.textInputEditLastName.getText().toString();
        phoneNumber = binder.textInputEditPhoneNumber.getText().toString();

        if(validateAccountDetails())
        {
            Intent signUpWithPasswordIntent = new Intent(requireActivity(), AuthorizationActivity.class);
            signUpWithPasswordIntent.putExtra("ACTION", AuthorizationActivity.SIGN_UP_WITH_GOOGLE);
            signUpWithPasswordIntent.putExtra("FIRSTNAME", firstName);
            signUpWithPasswordIntent.putExtra("LASTNAME", lastName);
            signUpWithPasswordIntent.putExtra("PHONENUMBER", phoneNumber);
            startActivityForResult(signUpWithPasswordIntent, AuthorizationActivity.AUTHENTICATION_REQUEST_CODE);
        }
        else
        {
            binder.textHelpMessage.setText(helperMessage);
        }
    }

    private boolean validateAccountDetails(){
        return validateName() && validatePhoneNumber();
    }

    private boolean validateName() {
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
}