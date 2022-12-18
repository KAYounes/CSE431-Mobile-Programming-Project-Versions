package com.example.aklny_v30.ui_controllers.d_signInScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityLoginScreenBinding;
import com.example.aklny_v30.ui_controllers.e_homeScreen.HomeScreenActivity;
import com.example.aklny_v30.ui_controllers.c_signUpScreen.SignUpScreenActivity;
import com.example.aklny_v30.viewModels.LoginScreenViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class LoginScreenActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_SIGN_IN = 100;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient googleSignInClient;

    private LoginScreenViewModel viewModel;

    LoginScreenDialog dialog;
    ActivityLoginScreenBinding binder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_screen);
        binder = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        // Init dialog
        dialog = new LoginScreenDialog(this);

        // Init View Model
        viewModel = new ViewModelProvider(this).get(LoginScreenViewModel.class);

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

        if(requestCode == REQUEST_CODE_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Exception taskException = task.getException();
            if(task.isSuccessful())
            {
                try
                {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("PRINT", "LoginScreenActivity > onActivityResult > task is successful > no API exception");
                    firebaseAuthWithGoogle(account.getIdToken());
                }
                catch (ApiException apiException)
                {
                    Log.w("PRINT", "LoginScreenActivity > onActivityResult > task is successful > API exception");
                    dialog.failedLoginDialog();
                }
            }


        }
    }

    private void signInWithGoogle() {
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

        dialog.startLoadingDialog();
        firebaseAuthWithPassword(email, password);
    }

    private boolean validateEmail(String email) {
        email.trim();
        String emailPattern = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        return (!email.isEmpty() && email.matches(emailPattern));
    }

    private boolean validatePassword(String password) {
        return !password.isEmpty();
    }

    private void firebaseAuthWithPassword(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("PRINT", "signInWithPassword: task is successful");
                            viewModel.assignCurrentUser(firebaseAuth.getUid());
                            startActivity(new Intent(LoginScreenActivity.this, HomeScreenActivity.class));
                            dialog.successfulLoginDialog();
                        } else {
                            dialog.failedLoginDialog();
                            Log.w("PRINT", "signInWithPassword: task failed");
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    onFirebaseAuthSuccess();
                }
                else
                {
                    Toast.makeText(LoginScreenActivity.this, "firebaseAuthWithGoogle > Failed to connect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void onFirebaseAuthSuccess(){
        FirebaseUser user = firebaseAuth.getCurrentUser();

        viewModel.checkUserExists(user.getUid()).subscribe(new SingleObserver<LoginScreenViewModel.userAccountStatus>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {return;}

            @Override
            public void onSuccess(LoginScreenViewModel.@io.reactivex.rxjava3.annotations.NonNull userAccountStatus userAccountStatus) {
                Toast.makeText(LoginScreenActivity.this, "Flag> " + userAccountStatus, Toast.LENGTH_SHORT).show();
                Log.d("PRINT", "onFirebaseAuthSuccess > Single success");

                if(userAccountStatus == LoginScreenViewModel.userAccountStatus.USER_DOES_NOT_EXISTS)
                {
                    startActivity(new Intent(LoginScreenActivity.this, SignUpScreenActivity.class));
                }
                else
                {
                    viewModel.assignCurrentUser(firebaseAuth.getUid());
                    startActivity(new Intent(LoginScreenActivity.this, HomeScreenActivity.class));
                }
                finish();
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Toast.makeText(LoginScreenActivity.this, "Failed to Connect", Toast.LENGTH_SHORT).show();
                Log.d("PRINT", "onFirebaseAuthSuccess > Single error");
            }
        });
    }
}