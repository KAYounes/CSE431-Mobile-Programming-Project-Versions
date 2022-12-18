package com.example.aklny_v30.ui_controllers.c_signUpScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aklny_v30.AuthorizationActivity;
import com.example.aklny_v30.R;
import com.example.aklny_v30.TestActivity;
import com.example.aklny_v30.databinding.FragmentSignUpWithGoogleBinding;
import com.example.aklny_v30.databinding.FragmentSignUpWithPasswordBinding;
import com.example.aklny_v30.ui_controllers.d_signInScreen.LoginScreenActivity;
import com.example.aklny_v30.ui_controllers.e_homeScreen.HomeScreenActivity;
import com.example.aklny_v30.viewModels.LoginScreenViewModel;
import com.example.aklny_v30.viewModels.SignUpViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class FragmentSignUpWithGoogle extends Fragment {
    private static final int REQUEST_CODE_SIGN_IN = 1000;
    private SignUpViewModel viewModel;
    private FragmentSignUpWithGoogleBinding binder;
    private GoogleSignInClient googleSignInClient;
    private String helperMessage;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binder = FragmentSignUpWithGoogleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);


        binder.btnSignUpWithGoogle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               signIn();
           }
        });

        return binder.getRoot();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("PRINT", "onActivityResult > "
                + requestCode
                + " - " + resultCode
                + " - " + data.getStringExtra(AuthorizationActivity.RESULT_KEY));

        if(requestCode == AuthorizationActivity.AUTHENTICATION_REQUEST_CODE){
            String result = data.getStringExtra(AuthorizationActivity.RESULT_KEY);
            if(result.equals(AuthorizationActivity.AUTHENTICATION_SUCCESS)){
                Toast.makeText(requireActivity(), "Account Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity(), HomeScreenActivity.class));
                requireActivity().finish();
            }

            Toast.makeText(requireActivity(), data.getStringExtra(AuthorizationActivity.RESULT_KEY), Toast.LENGTH_SHORT).show();
        }
//        if(requestCode == REQUEST_CODE_SIGN_IN)
//        {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            Exception taskException = task.getException();
//            if(task.isSuccessful())
//            {
//                try
//                {
//                    GoogleSignInAccount account = task.getResult(ApiException.class);
//                    Log.d("PRINT", "LoginScreenActivity > onActivityResult > task is successful > no API exception");
//                    firebaseAuthWithGoogle(account.getIdToken());
//                }
//                catch (ApiException apiException)
//                {
//                    Log.w("PRINT", "LoginScreenActivity > onActivityResult > task is successful > API exception");
//                }
//            }
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder = null;
    }
}